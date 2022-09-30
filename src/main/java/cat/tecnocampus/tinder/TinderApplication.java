package cat.tecnocampus.tinder;

import cat.tecnocampus.tinder.domain.Like;
import cat.tecnocampus.tinder.domain.Profile;
import cat.tecnocampus.tinder.persistence.LikeRepository;
import cat.tecnocampus.tinder.persistence.ProfileRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.transaction.Transactional;

@SpringBootApplication
public class TinderApplication implements CommandLineRunner {
    ProfileRepository profileRepository;
    LikeRepository likeRepository;

    public TinderApplication(ProfileRepository profileRepository, LikeRepository likeRepository) {
        this.profileRepository = profileRepository;
        this.likeRepository = likeRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(TinderApplication.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Profile popo = new Profile();
        popo.setAttraction(Profile.Gender.Bisexual);
        popo.setEmail("popo@tecnocampus.cat");
        popo.setGender(Profile.Gender.Man);
        popo.setNickname("Popopopo");
        popo.setPassion(Profile.Passion.Dance);

        profileRepository.save(popo);

        profileRepository.findAll().stream().forEach(System.out::println);

        Like like = likeRepository.findFirstByOriginEmailEqualsIgnoreCaseAndTargetEmailEqualsIgnoreCase("josep@tecnocampus.cat", "jordi@tecnocampus.cat")
                .get();
        System.out.println(like);

        Like like2 = likeRepository.findLike("josep@tecnocampus.cat", "jordi@tecnocampus.cat")
                .get();
        System.out.println(like2);
    }
}
