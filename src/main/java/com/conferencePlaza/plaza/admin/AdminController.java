package com.conferencePlaza.plaza.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final ConferenceRepository conferenceRepository;

    AdminController(ConferenceRepository c){
        this.conferenceRepository = c;
    }

    @PostMapping
    @RequestMapping("/conference")
    public ResponseEntity<PostConferenceResponse> registerConference(@RequestBody Conference request){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null && authentication.isAuthenticated()){
            String username = authentication.getName(); // user email extracted here
            if(!username.contains("admin")){
                return ResponseEntity.ok(new PostConferenceResponse("Only admin can register a conference!"));
            }
            else{
                conferenceRepository.save(request);
                return ResponseEntity.ok(new PostConferenceResponse("Conference posted sucessfully!"));
            }
        }
        else{
            return ResponseEntity.ok(new PostConferenceResponse("Cannot save conference as unauthenticated user!"));
        }

    }

}
