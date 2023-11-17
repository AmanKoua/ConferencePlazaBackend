package com.conferencePlaza.plaza.admin;

import com.conferencePlaza.plaza.user.User;
import com.conferencePlaza.plaza.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final ConferenceRepository conferenceRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    AdminController(ConferenceRepository c, UserRepository u, PasswordEncoder p){
        this.conferenceRepository = c;
        this.userRepository = u;
        this.passwordEncoder = p;
    }

    @PostMapping
    @RequestMapping("/conference")
    public ResponseEntity<PostItemResponse> registerConference(@RequestBody Conference request){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null && authentication.isAuthenticated()){
            String username = authentication.getName(); // user email extracted here
            if(!username.contains("admin")){
                return ResponseEntity.ok(new PostItemResponse("Only admin can register a conference!"));
            }
            else{
                conferenceRepository.save(request);
                return ResponseEntity.ok(new PostItemResponse("Conference posted sucessfully!"));
            }
        }
        else{
            return ResponseEntity.ok(new PostItemResponse("Cannot save conference as unauthenticated user!"));
        }

    }

    @PostMapping
    @RequestMapping("/chair")
    public ResponseEntity<PostItemResponse> registerChair(@RequestBody User request){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null && authentication.isAuthenticated()){
            String username = authentication.getName(); // user email extracted here
            if(!username.contains("admin")){
                return ResponseEntity.ok(new PostItemResponse("Only admin can register a new chair account!"));
            }
            else{
                request.setPassword(passwordEncoder.encode(request.getPassword()));
                userRepository.save(request);

                Optional<User> newChair = userRepository.findUserByEmail(request.getEmail());

                if(newChair.isEmpty()){
                    System.out.println("chair is empty after being registerd!");
                    return null;
                }

                return ResponseEntity.ok(new PostItemResponse("Chair posted sucessfully! -ChairId-" + newChair.get().getId()));
            }
        }
        else{
            return ResponseEntity.ok(new PostItemResponse("Cannot save chair as unauthenticated user!"));
        }

    }

}
