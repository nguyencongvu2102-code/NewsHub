package com.newsHub.configuration;

import com.newsHub.Repository.RoleRepository;
import com.newsHub.Repository.UserRepository;
import com.newsHub.entity.Role;
import com.newsHub.entity.User;
import com.newsHub.exception.AppException;
import com.newsHub.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class ApplicationConfig {
    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository){
        return args -> {

            Role adminRole = roleRepository.findByName("ADMIN").orElseGet(
                    () -> {
                        Role role = Role.builder()
                                .name("ADMIN")
                                .build();

                        return roleRepository.save(role);
                    }
            );

            if(userRepository.findByEmail("admin@gamil.com").isEmpty()){


                Set<Role> roles = new HashSet<>();
                roles.add(adminRole);
                User user = User.builder()
                        .email("admin@gamil.com")
                        .passWord(passwordEncoder.encode("admin"))
                        .roles(roles)
                        .build();

                userRepository.save(user);
                log.warn("admin user has been create with default password : admin,please change it");

            }

        };
    }
}
