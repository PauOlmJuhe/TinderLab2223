package cat.tecnocampus.tinder.persistence;

import cat.tecnocampus.tinder.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, String> {

}
