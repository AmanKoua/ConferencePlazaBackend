package com.conferencePlaza.plaza.user;

import com.conferencePlaza.plaza.admin.Conference;
import com.conferencePlaza.plaza.admin.ConferenceRepository;
import com.conferencePlaza.plaza.admin.PostItemResponse;
import com.conferencePlaza.plaza.auth.LoginRequestResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;
    private final ConferenceRepository conferenceRepository;
    private final PaperRepository paperRepository;
    private final PaperCoAuthorsRepository paperCoAuthorsRepository;

    UserController(UserRepository u, ConferenceRepository c, PaperRepository p, PaperCoAuthorsRepository p1){
        this.userRepository = u;
        this.conferenceRepository = c;
        this.paperRepository = p;
        this.paperCoAuthorsRepository = p1;
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

    @PostMapping
    @RequestMapping("/paper")
    public ResponseEntity<PostItemResponse> postPaper(@RequestBody PaperSlice request){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null && authentication.isAuthenticated()){
            String username = authentication.getName(); // user email extracted here
            Optional<User> tempUser = userRepository.findUserByEmail(username);

            if(!tempUser.isPresent()){
                System.out.println("------ User not found when retrieving conferences! ----");
                return null;
            }

            if(!tempUser.get().getType().contains("Author")){
                return ResponseEntity.ok(new PostItemResponse("Only authors can post papers!"));
            }

            Optional<Conference> tempConference = conferenceRepository.findById(request.getConferenceId());

            if(!tempConference.isPresent()){
                return ResponseEntity.ok(new PostItemResponse("No conference found for given id!"));
            }

            Paper tempPaper = new Paper(request.paperData, request.paperTitle,  tempUser.get().getId(), "Pending", tempConference.get().getId());
            paperRepository.save(tempPaper);

            if(request.coAuthors.size() > 0){

                List<String> coAuthors = request.coAuthors;

                for(int i = 0; i < coAuthors.size(); i++){

                    Optional<User> tempCoAuthor = userRepository.findPlazaUserByUsername(coAuthors.get(i));

                    if(tempCoAuthor.isEmpty()){
                        continue;
                    }
                    else{
                        paperCoAuthorsRepository.save(new PaperCoAuthors(tempPaper.getId(), tempCoAuthor.get().getId()));
                    }

                }

            }

            return ResponseEntity.ok(new PostItemResponse("Paper added sucessfully!"));

        }
        return null;
    }

}
