package cat.tecnocampus.tinder.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tinder_like")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Profile origin;
    @ManyToOne(fetch = FetchType.LAZY)
    //@JsonBackReference
    private Profile target;
    private boolean matched;
    private LocalDate creationDate;
    private LocalDate matchDate;

    public Like() {
    }

    public Like(Profile origin, Profile target) {
        this.origin = origin;
        this.target = target;
        creationDate = LocalDate.now();
        matched = false;
        matchDate = null;
    }

    public Profile getTarget() {
        return target;
    }

    public void setTarget(Profile target) {
        this.target = target;
    }

    public boolean isMatched() {
        return matched;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
        this.matchDate = LocalDate.now();
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate date) {
        this.creationDate = date;
    }

    public LocalDate getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(LocalDate matchDate) {
        this.matchDate = matchDate;
    }

    @Override
    public String toString() {
        return "Like{" +
                "Id=" + Id +
                ", origin=" + origin +
                ", target=" + target +
                ", matched=" + matched +
                ", creationDate=" + creationDate +
                ", matchDate=" + matchDate +
                '}';
    }
}
