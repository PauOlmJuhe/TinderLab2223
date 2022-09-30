package cat.tecnocampus.tinder.persistence;

import cat.tecnocampus.tinder.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    @Query("select l from Like l where upper(l.origin.email) = upper(:email) " +
            "and upper(l.target.email) = upper(:email1)")
    Optional<Like> findLike(@Param("email") String email, @Param("email1") String email1);

   Optional<Like> findFirstByOriginEmailEqualsIgnoreCaseAndTargetEmailEqualsIgnoreCase(String origin, String target);
}
