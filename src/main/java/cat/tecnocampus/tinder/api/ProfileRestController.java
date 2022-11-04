package cat.tecnocampus.tinder.api;

import cat.tecnocampus.tinder.application.TinderController;
import cat.tecnocampus.tinder.domain.Like;
import cat.tecnocampus.tinder.domain.Profile;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;


@RestController
public class ProfileRestController {

	private TinderController tinderController;

	public ProfileRestController(TinderController tinderController) {
		this.tinderController = tinderController;
	}

	@GetMapping("/profiles/{email}")
	public Profile getProfile(@PathVariable String email)  {
		return tinderController.getProfile(email);
	}

	@GetMapping("/profiles")
	public List<Profile> getProfiles() {
		return tinderController.getProfiles();
	}

	@GetMapping("/profiles/me")
	public Profile getProfileMe(Principal principal) {
		return tinderController.getProfileWithNickname(principal.getName());
	}


	@GetMapping("/likes")
	public List<Like> getLikes() {
		return tinderController.getLikes();
	}

	//Returns profiles that match the registered username preferences
	@GetMapping("/profiles/me/candidates")
	public List<Profile> getCandidatesByNickname(Principal principal) {
		return tinderController.getCandidatesByNickname(principal.getName());
	}

	//Returns profiles that match the user (email) preferences
	@GetMapping("/profiles/{email}/candidates")
	public List<Profile> getCandidates(@PathVariable String email) {
		return tinderController.getCandidates(email);
	}

	@PostMapping("/profiles")
	public Profile addProfile(@RequestBody @Valid Profile profile) {
		return tinderController.addProfile(profile);
	}

	@PostMapping("/likes")
	public void addLikes(@RequestBody LikeFront likes) {
		tinderController.newLikes(likes.getOrigin(), likes.getTargets());
	}


	private static class LikeFront {
		private String origin;
		private List<String> targets;

		public LikeFront() {}
		public String getOrigin() {
			return origin;
		}
		public void setOrigin(String origin) {
			this.origin = origin;
		}
		public List<String> getTargets() {
			return targets;
		}
		public void setTargets(List<String> targets) {
			this.targets = targets;
		}
	}
}
