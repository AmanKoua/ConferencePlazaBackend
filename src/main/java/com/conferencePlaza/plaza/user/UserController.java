package com.conferencePlaza.plaza.user;

import com.conferencePlaza.plaza.admin.Conference;
import com.conferencePlaza.plaza.admin.ConferenceRepository;
import com.conferencePlaza.plaza.admin.PostItemResponse;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
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
    private final ReviewerAssignmentRepository reviewerAssignmentRepository;

    UserController(UserRepository u, ConferenceRepository c, PaperRepository p, PaperCoAuthorsRepository p1, ReviewerAssignmentRepository r){
        this.userRepository = u;
        this.conferenceRepository = c;
        this.paperRepository = p;
        this.paperCoAuthorsRepository = p1;
        this.reviewerAssignmentRepository = r;
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

    // Get a single conference's data by id as an author
    @GetMapping
    @RequestMapping("/author/conference")
    public ResponseEntity<List<GetConferenceResponse>> getConferenceData(@RequestParam Optional<String> conferenceId){

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

            List<Paper> userPapers = paperRepository.getPapersByConferenceIdAndAuthorId(tempConference.get().getId(), tempUser.get().getId());

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

    @GetMapping
    @RequestMapping("/chair/conference")
    public ResponseEntity<List<GetConferenceResponse>> getAllSubmissionsToConference(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null && authentication.isAuthenticated()){
            String username = authentication.getName(); // user email extracted here
            Optional<User> tempUser = userRepository.findUserByEmail(username);

            if(!tempUser.isPresent()){
                System.out.println("------ User not found when retrieving conference submissions! ----");
                return null;
            }

            Optional<Conference> tempConference = conferenceRepository.getConferenceByChairId(tempUser.get().getId());

            if(tempConference.isEmpty()){
                System.out.println("------ no conference found when retrieving chair conference! ----");
                return null;
            }

            List<Paper> conferencePapers = paperRepository.getPapersByConferenceId(tempConference.get().getId());

            if(conferencePapers.isEmpty()){
                System.out.println("------ no papers submitted to conference ----");
                return ResponseEntity.ok(new ArrayList<>());
            }

            List<GetConferenceResponse> responsePayload = new ArrayList<>();

            for(int i = 0; i < conferencePapers.size(); i++){

                List<PaperCoAuthors> tempPaperCoAuthors = paperCoAuthorsRepository.findCoAuthorsForSubmission(conferencePapers.get(i).getId());
                List<String> tempPaperCoAuthorsNames = new ArrayList<>();

                Optional<User> tempAuthor = userRepository.findById(conferencePapers.get(i).getAuthorId());

                if(tempAuthor.isEmpty()){
                    System.out.println("Author of conference submission does not exist!");
                    continue;
                }

                String authorName = tempAuthor.get().getFirstName() + " " + tempAuthor.get().getLastName();

                for(int j = 0; j < tempPaperCoAuthors.size(); j++){
                    Optional<User> tempCoAuthor = userRepository.findById(tempPaperCoAuthors.get(j).getUserId());

                    if(tempCoAuthor.isEmpty()){
                        continue;
                    }

                    tempPaperCoAuthorsNames.add(tempCoAuthor.get().getFirstName() + " " + tempCoAuthor.get().getLastName());
                }

                GetConferenceResponse temp = new GetConferenceResponse(conferencePapers.get(i).getPaperTitle(), authorName, tempPaperCoAuthorsNames, conferencePapers.get(i).getStatus());
                responsePayload.add(temp);
            }
            return ResponseEntity.ok(responsePayload);
        }
        return null;
    }

    @GetMapping
    @RequestMapping("/chair/allreviewers")
    public ResponseEntity<List<User>> getAllReviewers(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null && authentication.isAuthenticated()) {

            List<User> responsePayload = new ArrayList<>();
            List<User> unprocessedUsers = userRepository.findAllReviewers();

            for(int i = 0; i < unprocessedUsers.size(); i++){
                User temp = unprocessedUsers.get(i);
                temp.setPassword("REDACTED");
                responsePayload.add(temp);
            }

            return ResponseEntity.ok(responsePayload);

        }

        return null;

    }

    @PostMapping
    @RequestMapping("/chair/reviewer")
    public ResponseEntity<String> assignReviewerToPaper(@RequestBody @NonNull Optional<ReviewerAssignment> request){

        if(request.isEmpty()){
            System.out.println("---- assign reviewer to paper endpoint had not request body! ---- ");
            return null;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null && authentication.isAuthenticated()) {

            Optional<User> requester = userRepository.findUserByEmail(authentication.getName());

            if(requester.isEmpty()){
                System.out.println("--------- requester not found when posing review assignment! ----------");
                return null;
            }

            if(!requester.get().getType().contains("Chair")){
                System.out.println("--------- Only chair can make reviewer assignments! ------------");
                return null;
            }

            Optional<User> reviewer = userRepository.findById(request.get().getReviewerId());

            if(reviewer.isEmpty()){
                System.out.println("------ assigned reviewer not found! --------");
                return null;
            }

            if(!reviewer.get().getType().contains("Reviewer")){
                System.out.println("------ can only assign a reviewer to a reviwer assignment! -----");
                return null;
            }

            reviewerAssignmentRepository.save(request.get());
            return ResponseEntity.ok("Reviewer assignment saved successfully");

        }

        return null;

    }

    @GetMapping
    @RequestMapping("/chair/paper-reviews")
    public ResponseEntity<List<ReviewerAssignment>> getReviewsForPaper (@Param("paperId") Long id){

        // TODO fix get paper reviews endpoint

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null && authentication.isAuthenticated()) {
            List<ReviewerAssignment> reviewerAssignments = reviewerAssignmentRepository.getReviewerAssignmentsByPaperId(id);
            return ResponseEntity.ok(reviewerAssignments);
        }

        return null;

    }

    @PostMapping
    @RequestMapping("/chair/paper-decision")
    public ResponseEntity<String> setPaperDecision (@RequestBody PaperDecisionRequest request){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null && authentication.isAuthenticated()) {

            Optional<User> requester = userRepository.findUserByEmail(authentication.getName());

            if(requester.isEmpty()){
                System.out.println("--------- requester not found when posting paper decision! ----------");
                return null;
            }

            if(!requester.get().getType().contains("Chair")){
                System.out.println("--------- Only chair can make paper decisions! ------------");
                return null;
            }

            paperRepository.setPaperDecision(request.getPaperId(), request.getDecision());
            return ResponseEntity.ok("Paper decision sucessfully posted!");

        }

        return null;

    }

    @GetMapping
    @RequestMapping("/reviewer/papers")
    public ResponseEntity<List<GetAssignedPaper>> getAssignedPapers (){

        // TODO Test that assigned papers are retieved with all relevant details

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null && authentication.isAuthenticated()) {

            Optional<User> requester = userRepository.findUserByEmail(authentication.getName());

            if (requester.isEmpty()) {
                System.out.println("---- requesting review papers for a non-existent reviewer! -----");
                return null;
            }

            if (!requester.get().getType().contains("Reviewer")) {
                System.out.println("----- must be a reviewer to retrieve assigned papers! ------");
                return null;
            }

            List<Paper> assignedPapers = paperRepository.getAssignedPapers(requester.get().getId());
            List<GetAssignedPaper> responsePayload = new ArrayList<>();

            for(int i = 0; i < assignedPapers.size(); i++){

                Paper tempPaper = assignedPapers.get(i);
                Optional<User> tempPaperAuthorOptional = userRepository.findById(tempPaper.getAuthorId());

                if(tempPaperAuthorOptional.isEmpty()){
                    System.out.println("---- paper author is empty when getting reviewer papers! ----");
                    continue;
                }

                User paperAuthor = tempPaperAuthorOptional.get();
                List<PaperCoAuthors> tempPaperCoAuthors = paperCoAuthorsRepository.findCoAuthorsForSubmission(tempPaper.getId());
                List<String> tempPaperCoAuthorsNames = new ArrayList<>();

                for(int j = 0; j < tempPaperCoAuthors.size(); j++){

                    Optional<User> tempCoAuthor = userRepository.findById(tempPaperCoAuthors.get(i).getUserId());

                    if(tempCoAuthor.isEmpty()){
                        System.out.println(" ------ Temp co-author not found when looking for co-authors -----");
                        continue;
                    }

                    tempPaperCoAuthorsNames.add(tempCoAuthor.get().getFirstName() + " " + tempCoAuthor.get().getLastName());

                }

                GetAssignedPaper temp = new GetAssignedPaper();

                temp.setPaperId(tempPaper.getId());
                temp.setPaperData(tempPaper.getPaperData());
                temp.setPaperTitle(tempPaper.getPaperTitle());
                temp.setAuthorId(tempPaper.getAuthorId());
                temp.setAuthorName(paperAuthor.getFirstName() + " " + paperAuthor.getLastName());
                temp.setCoAuthorNames(tempPaperCoAuthorsNames);
                temp.setStatus(tempPaper.getStatus());
                temp.setConferenceId(tempPaper.getConferenceId());

                responsePayload.add(temp);

            }

            return ResponseEntity.ok(responsePayload);

        }

        return null;

    }

    @GetMapping
    @RequestMapping("/reviewer/reviews")
    public ResponseEntity<List<ReviewerAssignment>> getReviewerAssignments (){ // Get the recommendation the reviewer assigned to an existing paper

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null && authentication.isAuthenticated()) {

            Optional<User> requester = userRepository.findUserByEmail(authentication.getName());

            if(requester.isEmpty()){
                System.out.println("---- requesting review assignments for a reviewer is empty! -----");
                return null;
            }

            if(!requester.get().getType().contains("Reviewer")){
                System.out.println("----- must be a reviewer to retrieve assigned reviews! ------");
                return null;
            }

            List<ReviewerAssignment> assignments = reviewerAssignmentRepository.getReviewerAssignmentsByUserId(requester.get().getId());
            return ResponseEntity.ok(assignments);

        }

        return null;

    }

    @PostMapping
    @RequestMapping("/reviewer/review")
    public ResponseEntity<String> setReviewDecision (@RequestBody ReviewerAssignment request){

        // TODO : Fix reviewer assignment query

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null && authentication.isAuthenticated()) {

            Optional<User> requester = userRepository.findUserByEmail(authentication.getName());

            if(requester.isEmpty()){
                System.out.println("---- requester is empty! -----");
                return null;
            }

            if(!requester.get().getType().contains("Reviewer")){
                System.out.println("----- must be a reviewer to retrieve assigned reviews! ------");
                return null;
            }

            reviewerAssignmentRepository.setReviewerAssignment(request.getPaperId(), request.getReviewerId(), request.getStatus());
            return ResponseEntity.ok("Successfully set review response!");

        }

        return null;

    }

}
