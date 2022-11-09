package cat.tecnocampus.tinder.configuration.security;

import cat.tecnocampus.tinder.domain.UserLab;
import cat.tecnocampus.tinder.persistence.UserLabRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class TinderUserDetailsService implements UserDetailsService {
    private UserLabRepository userLabRepository;

    public TinderUserDetailsService(UserLabRepository userLabRepository) {
        this.userLabRepository = userLabRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserLab user = userLabRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return TinderUserDetails.build(user);
    }

}