package com.conferencePlaza.plaza.user;

import com.conferencePlaza.plaza.admin.Conference;
import com.conferencePlaza.plaza.admin.ConferenceRepository;
import com.conferencePlaza.plaza.auth.LoginRequestResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;
    private final ConferenceRepository conferenceRepository;

    UserController(UserRepository u, ConferenceRepository c){
        this.userRepository = u;
        this.conferenceRepository = c;
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

    @GetMapping
    @RequestMapping("/conferences")
    public ResponseEntity<GetConferencesResponse> getConferences(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null && authentication.isAuthenticated()){
            String username = authentication.getName(); // user email extracted here
            Optional<User> tempUser = userRepository.findUserByEmail(username);

            if(!tempUser.isPresent()){
                System.out.println("------ User not found when retrieving conferences! ----");
                return null;
            }

            List<Conference> tempConferences = conferenceRepository.findAll();
            GetConferencesResponse tempResponse = new GetConferencesResponse(tempConferences);
            return ResponseEntity.ok(tempResponse);

        }
        return null;
    }

}
