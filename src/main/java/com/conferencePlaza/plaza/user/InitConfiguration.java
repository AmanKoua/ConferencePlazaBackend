package com.conferencePlaza.plaza.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitConfiguration {

    private final UserRepository userRepository;

    InitConfiguration(UserRepository u){
        this.userRepository = u;
    }

    @Bean
    CommandLineRunner userDataInitializer(UserRepository userRepository){
      return args -> {
        User admin = new User(
                "admin@gmail.com",
                "admin",
                "pass"
        )
      }
    };

}
