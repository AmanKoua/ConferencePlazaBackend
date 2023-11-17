package com.conferencePlaza.plaza.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
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
