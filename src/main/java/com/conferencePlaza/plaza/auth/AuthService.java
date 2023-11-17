package com.conferencePlaza.plaza.auth;

import com.conferencePlaza.plaza.config.JwtService;
import com.conferencePlaza.plaza.user.User;
import com.conferencePlaza.plaza.user.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    AuthService(UserRepository u, PasswordEncoder p, JwtService j, AuthenticationManager a){
        this.userRepository = u;
        this.passwordEncoder = p;
        this.jwtService = j;
        this.authManager = a;
    }

    public LoginRequestResponse authenticate(LoginRequestBody request){
        Optional<User> tempUser = userRepository.findUserByEmail(request.getEmail());

        if(tempUser.isEmpty()){
            System.out.println("--- user not found when loggging in! ---");
            return null;
        }

        boolean isCorrectPassword = passwordEncoder.matches(request.getPassword(),tempUser.get().getPassword());

        if(!isCorrectPassword){
            System.out.println("--- the passwords did not match up when logging in! ----");
            return null;
        }

        var jwtToken = jwtService.generateToken(tempUser.get());

        return new LoginRequestResponse(jwtToken, tempUser.get().getType());
    }

}