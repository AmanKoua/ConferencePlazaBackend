package com.conferencePlaza.plaza.user;

import com.conferencePlaza.plaza.auth.LoginRequestResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;

    UserController(UserRepository u){
        this.userRepository = u;
    }

    @GetMapping
    public ResponseEntity<User> getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null && authentication.isAuthenticated()){
            String username = authentication.getName(); // user email extracted here
            return ResponseEntity.ok(userRepository.findUserByEmail(username).get());
        }

        return ResponseEntity.ok(new User());

    }

}
