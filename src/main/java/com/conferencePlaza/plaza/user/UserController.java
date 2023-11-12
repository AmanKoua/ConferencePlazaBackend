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
    @GetMapping
    public ResponseEntity<String> getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null && authentication.isAuthenticated()){
            String username = authentication.getName(); // user email extracted here
            System.out.println(username);
            return ResponseEntity.ok("good to go!");
        }

        return ResponseEntity.ok("Authentication is null or you're not authenticated");

    }

}
