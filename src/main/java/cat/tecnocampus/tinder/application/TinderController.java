package cat.tecnocampus.tinder.application;

import cat.tecnocampus.tinder.application.exception.ProfileNotFound;
import cat.tecnocampus.tinder.domain.Like;
import cat.tecnocampus.tinder.domain.Profile;
import cat.tecnocampus.tinder.persistence.LikeRepository;
import cat.tecnocampus.tinder.persistence.ProfileRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service //same as @Component
public class TinderController {
	private ProfileRepository profileRepository;
	private LikeRepository likeRepository;

	public TinderController(ProfileRepository profileRepository, LikeRepository likeRepository) {
		this.profileRepository = profileRepository;
		this.likeRepository = likeRepository;
	}

	public Profile getProfile(String email) {
		return profileRepository.findById(email).orElseThrow(() -> new ProfileNotFound(email));
	}

	public List<Profile> getProfiles() {
		return profileRepository.findAll();
	}

	public List<Profile> getCandidates(String email) {
		Profile user = this.getProfile(email);
		return this.getProfiles().stream()
				.filter(user::isCompatible)
				.collect(Collectors.toList());
	}

	public Profile addProfile(Profile profile) {
		return profileRepository.save(profile);
	}

	@Transactional
	public int newLikes(String originEmail, List<String> targetEmail) {
		Profile origin = profileRepository.findById(originEmail)
				.orElseThrow(() -> new ProfileNotFound(originEmail));
		List<Like> likes =
		targetEmail.stream().map(email -> profileRepository.findById(email).orElseThrow(() -> new ProfileNotFound(email)))
				.filter(origin::isCompatible) 			//make sure it is compatible
				.map(origin::createAndMatchLike)		//create likes
				.collect(Collectors.toList());
		origin.addLikes(likes);
		System.out.println("hola" + origin.getLikes());
		profileRepository.save(origin);
		likeRepository.saveAll(likes);
		List<Like> updatedTargetMatchingLikes = updateTargetMatchingLikes(origin, likes);
		likeRepository.saveAll(updatedTargetMatchingLikes);
		return likes.size();
	}

	private List<Like> updateTargetMatchingLikes(Profile origin, List<Like> likes) {
		return likes.stream().filter(Like::isMatched)
				.map(l -> likeRepository.findFirstByOriginEmailEqualsIgnoreCaseAndTargetEmailEqualsIgnoreCase(l.getTarget().getEmail(), origin.getEmail()).get())
				.map(l -> {l.setMatched(true);
					l.setMatchDate(LocalDate.now()); return l;}).collect(Collectors.toList());
	}

	public List<Like> getLikes() {
		return likeRepository.findAll();
	}
}
