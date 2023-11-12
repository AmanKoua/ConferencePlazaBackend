package com.conferencePlaza.plaza.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class InitConfiguration {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    InitConfiguration(UserRepository u, PasswordEncoder p){
        this.userRepository = u;
        this.passwordEncoder = p;
    }

    @Bean
    CommandLineRunner userDataInitializer(UserRepository userRepository){
      return args -> {
        User admin = new User(
                "admin@gmail.com",
                "admin",
                passwordEncoder.encode("password"),
                "Sir",
                "admin",
                "the admin",
                "n/a",
                "Admin"
        );
          User chair1 = new User(
                  "chair1@gmail.com",
                  "chair",
                  passwordEncoder.encode("password"),
                  "John",
                  "Smith",
                  "the chair",
                  "n/a",
                  "Chair"
          );
          User chair2 = new User(
                  "chair2@gmail.com",
                  "chair",
                  passwordEncoder.encode("password"),
                  "Bill",
                  "Jones",
                  "the chair",
                  "n/a",
                  "Chair"
          );
          User author1 = new User(
                  "author1@gmail.com",
                  "author",
                  passwordEncoder.encode("password"),
                  "Amy",
                  "Miller",
                  "the author",
                  "n/a",
                  "Author"
          );
          User reviewer1 = new User(
                  "reviewer1@gmail.com",
                  "reviewer",
                  passwordEncoder.encode("password"),
                  "Steven",
                  "lornenz",
                  "the retirever",
                  "n/a",
                  "Reviewer"
          );
          User reviewer2 = new User(
                  "reviewer2@gmail.com",
                  "reviewer",
                  passwordEncoder.encode("password"),
                  "billy",
                  "shacklebolt",
                  "the retirever",
                  "n/a",
                  "Reviewer"
          );
          User reviewer3 = new User(
                  "reviewer3@gmail.com",
                  "reviewer",
                  passwordEncoder.encode("password"),
                  "susan",
                  "leRoja",
                  "the retirever",
                  "n/a",
                  "Reviewer"
          );

          userRepository.saveAll(List.of(admin, chair1, chair2, author1, reviewer1, reviewer2, reviewer3));

      };
    };

}
