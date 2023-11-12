package com.conferencePlaza.plaza.user;

import com.conferencePlaza.plaza.admin.Conference;
import com.conferencePlaza.plaza.admin.ConferenceRepository;
import com.conferencePlaza.plaza.admin.PostItemResponse;
import com.conferencePlaza.plaza.auth.LoginRequestResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    // Get a single conference by id as an author
    @GetMapping
    @RequestMapping("/conference")
    public ResponseEntity<List<GetConferenceResponse>> getConference(@RequestParam Optional<String> conferenceId){

        if(conferenceId.isEmpty()){
            System.out.println("----- empty conference id param! -------");
            return null;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null && authentication.isAuthenticated()){
            String username = authentication.getName(); // user email extracted here
            Optional<User> tempUser = userRepository.findUserByEmail(username);

            if(!tempUser.isPresent()){
                System.out.println("------ User not found when retrieving conference! ----");
                return null;
            }

            Optional<Conference> tempConference = conferenceRepository.findById(Long.parseLong(conferenceId.get()));

            if(tempConference.isEmpty()){
                System.out.println("------ conference not found when retrieving conference! ----");
                return null;
            }

            List<Paper> userPapers = paperRepository.getPapersByConferenceId(tempConference.get().getId(), tempUser.get().getId());

            if(userPapers.size() == 0){
                System.out.println("------ no papers submitted to conference ----");
                return ResponseEntity.ok(new ArrayList<>());
            }

            String authorName = tempUser.get().getFirstName() + " " + tempUser.get().getLastName();
            List<GetConferenceResponse> responsePayload = new ArrayList<>();

            for(int i = 0; i < userPapers.size(); i++){

                List<PaperCoAuthors> tempPaperCoAuthors = paperCoAuthorsRepository.findCoAuthorsForSubmission(userPapers.get(i).getId());
                List<String> tempPaperCoAuthorsNames = new ArrayList<>();

                for(int j = 0; j < tempPaperCoAuthors.size(); j++){
                    Optional<User> tempCoAuthor = userRepository.findById(tempPaperCoAuthors.get(j).getUserId());

                    if(tempCoAuthor.isEmpty()){
                        continue;
                    }

                    tempPaperCoAuthorsNames.add(tempCoAuthor.get().getFirstName() + " " + tempCoAuthor.get().getLastName());
                }

                GetConferenceResponse temp = new GetConferenceResponse(userPapers.get(i).getPaperTitle(), authorName, tempPaperCoAuthorsNames, userPapers.get(i).getStatus());
                responsePayload.add(temp);
            }
            return ResponseEntity.ok(responsePayload);
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
