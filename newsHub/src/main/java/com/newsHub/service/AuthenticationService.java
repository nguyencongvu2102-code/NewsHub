package com.newsHub.service;

import com.newsHub.Repository.InvalidatedTokenRepository;
import com.newsHub.Repository.UserRepository;
import com.newsHub.dto.request.AuthenticationRequest;
import com.newsHub.dto.request.IntrospectRequest;
import com.newsHub.dto.request.LogOutRequest;
import com.newsHub.dto.request.RefreshTokenRequest;
import com.newsHub.dto.response.AuthenticationResponse;
import com.newsHub.dto.response.IntrospectResponse;
import com.newsHub.entity.InvalidateToken;
import com.newsHub.entity.User;
import com.newsHub.exception.AppException;
import com.newsHub.exception.ErrorCode;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)

public class AuthenticationService {
    PasswordEncoder passwordEncoder;
    UserRepository userRepository;
    InvalidatedTokenRepository invalidatedTokenRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String signerKey;

    @NonFinal
    @Value("${jwt.valid_duration}")
    private long validDuration;

    @NonFinal
    @Value("${jwt.refresh_duration}")
    private long refreshDuration;




    public AuthenticationResponse createAuthentication(AuthenticationRequest authenticationRequest){
        User user = userRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow(
                ()-> new AppException(ErrorCode.USER_NOT_FOUND)
        );

        var authenticated = passwordEncoder.matches(authenticationRequest.getPassWord(), user.getPassWord());
        if(!authenticated){
            throw new AppException(ErrorCode.AUTHENTICATED);
        }

        var token = generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();


    }


    private String generateToken(User user){
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getEmail())
                .issuer("vuDev")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(validDuration,ChronoUnit.SECONDS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope",buildScope(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(jwsHeader,payload);

        try {
            jwsObject.sign(new MACSigner(signerKey));
            return jwsObject.serialize();

        } catch (JOSEException e) {
            log.error("Cannot error token",e);
            throw new RuntimeException(e);
        }


    }

    private String buildScope(User user){
        StringJoiner stringJoiner = new StringJoiner(" ");

        if(!CollectionUtils.isEmpty(user.getRoles())){
           user.getRoles().forEach(role -> {
               stringJoiner.add("ROLE_" + role.getName());
               if (!CollectionUtils.isEmpty(role.getPermissions())){
                   role.getPermissions().forEach(permission -> {
                       stringJoiner.add(permission.getName());
                   });
               }
           });

        }

        return stringJoiner.toString();
    }


    private SignedJWT verifyToken(String token,boolean isRefresh)
            throws JOSEException, ParseException {
        JWSVerifier jwsVerifier = new MACVerifier(signerKey.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = (isRefresh) ?
                new Date(signedJWT.getJWTClaimsSet().getIssueTime().toInstant().plus(
                       refreshDuration,ChronoUnit.SECONDS
                ).toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(jwsVerifier);


        if(!(verified && expiryTime.after(new Date()))){
            throw new AppException(ErrorCode.AUTHENTICATED);
        }

        if(invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())){
            throw new AppException(ErrorCode.AUTHENTICATED);
        }

        return signedJWT;

    }


    public IntrospectResponse introspectResponse(IntrospectRequest introspectRequest)
            throws ParseException, JOSEException {
        var token = introspectRequest.getToken();
        boolean isValid = true;

        try {
            verifyToken(token,false);
        }catch (Exception _){
            isValid = false;
        }

        return IntrospectResponse.builder()
                .valid(isValid)
                .build();
    }



    public void  logOut(LogOutRequest request) throws ParseException, JOSEException {
        try {
            var signToken = verifyToken(request.getToken(), true);
            String jit = signToken.getJWTClaimsSet().getJWTID();
            Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();
            InvalidateToken invalidateToken = InvalidateToken
                    .builder()
                    .jid(jit)
                    .expiryTime(expiryTime)
                    .build();

            invalidatedTokenRepository.save(invalidateToken);

        }catch (AppException _){
            log.info("Token already expired");
        }


    }

    public AuthenticationResponse reFresh(RefreshTokenRequest request)
            throws ParseException, JOSEException {
        var signerToken = verifyToken(request.getToken(),true);

        var jit = signerToken.getJWTClaimsSet().getJWTID();
        var expiryTime = signerToken.getJWTClaimsSet().getExpirationTime();

        InvalidateToken invalidateToken = InvalidateToken.builder()
                .jid(jit)
                .expiryTime(expiryTime)
                .build();

        invalidatedTokenRepository.save(invalidateToken);

        var email = signerToken.getJWTClaimsSet().getSubject();

        var user = userRepository.findByEmail(email).orElseThrow(
                ()-> new AppException(ErrorCode.USER_NOT_FOUND)
        );

        var token = generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }





}
