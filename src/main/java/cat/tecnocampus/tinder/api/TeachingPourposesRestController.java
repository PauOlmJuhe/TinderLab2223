package cat.tecnocampus.tinder.api;

import cat.tecnocampus.tinder.application.TeachingPurposesController;
import cat.tecnocampus.tinder.application.TinderController;
import cat.tecnocampus.tinder.api.frontendException.IncorrectRESTParameter;
import cat.tecnocampus.tinder.domain.Profile;
import cat.tecnocampus.tinder.persistence.ProfileRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;

@RestController
@RequestMapping("/teaching")
@Validated
public class TeachingPourposesRestController {
    private final TinderController tinderController;

    @Autowired
    ProfileRepository profileRepository;
    private TeachingPurposesController teachingPurposesControllerCreatedHere;

    private final TeachingPurposesController teachingPurposesControllerInjected;

    public TeachingPourposesRestController(TinderController tinderController, TeachingPurposesController teachingPurposesControllerInjected,
                                           ProfileRepository profileRepository) {
        this.tinderController = tinderController;
        this.teachingPurposesControllerInjected = teachingPurposesControllerInjected;
        this.teachingPurposesControllerCreatedHere = new TeachingPurposesController(profileRepository);
    }

    @GetMapping("/int/{i}")
    public int getInt(@PathVariable @Max(50) int i) {
        return i;
    }

    @PostMapping("/profilesString")
    public String addProfile(@RequestBody String profile) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        Profile profileObj = objectMapper.readValue(profile, Profile.class);

        Profile responseObj = tinderController.addProfile(profileObj);;

        return objectMapper.writeValueAsString(responseObj);
    }

    @GetMapping("/salutation")
    public String getSalutation(@RequestParam(defaultValue = "Anonymous") String name) {
        if (name.equalsIgnoreCase("exception"))
            throw new IncorrectRESTParameter("name", name);
        return "You must be the great " + name;
    }

    @GetMapping("/createTwoProfilesOneCreated")
    public void createTwoProfilesOneCreated() {
        teachingPurposesControllerCreatedHere.createTwoProfilesWithError();
    }

    @GetMapping("/createTwoProfilesNoneCreated")
    public void createTwoProfilesNoneCreated() {
        teachingPurposesControllerInjected.createTwoProfilesWithError();
    }

}
