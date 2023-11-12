package com.conferencePlaza.plaza.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    AuthController(AuthService a){
        authService = a;
    }

    @PostMapping
    public ResponseEntity<LoginRequestResponse> login (@RequestBody LoginRequestBody request){
        return ResponseEntity.ok(authService.authenticate(request));
    }

}
