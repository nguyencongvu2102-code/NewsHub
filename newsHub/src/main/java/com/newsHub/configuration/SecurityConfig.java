package com.newsHub.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private static final String[] POST_PUBLIC = {
            "/permissions"
            ,"/roles",
            "/auth/register","/auth/me"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity){
        httpSecurity.authorizeHttpRequests(request ->
                request.requestMatchers(HttpMethod.POST,POST_PUBLIC).permitAll()
                        .requestMatchers(HttpMethod.GET,POST_PUBLIC).permitAll()
                        .anyRequest().authenticated()
        );

        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        return httpSecurity.build();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }
}
