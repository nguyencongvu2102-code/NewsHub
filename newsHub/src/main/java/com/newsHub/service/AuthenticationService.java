package com.newsHub.service;

import com.newsHub.Repository.UserRepository;
import com.newsHub.dto.request.AuthenticationRequest;
import com.newsHub.dto.response.AuthenticationResponse;
import com.newsHub.entity.User;
import com.newsHub.exception.AppException;
import com.newsHub.exception.ErrorCode;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AuthenticationService {
    PasswordEncoder passwordEncoder;
    UserRepository userRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String signerKey;

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
                .expirationTime(new Date(Instant.now().plus(1,ChronoUnit.HOURS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope","buildScope")
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
}
