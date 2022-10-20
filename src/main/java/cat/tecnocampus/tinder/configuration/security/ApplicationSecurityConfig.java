package cat.tecnocampus.tinder.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ApplicationSecurityConfig {

    private final PasswordEncoder passwordEncoder;

    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers( "/", "index", "/css/*", "/js/*", "/*.html").permitAll()
                .antMatchers("/profiles/me/**").hasRole("USER")
                .antMatchers("/profiles/**", "/profilesByName/**").hasRole("ADMIN")
                .anyRequest()
                .authenticated()

                .and()
                .httpBasic()

                .and()
                .csrf().disable()
                .cors().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

    @Bean
    protected UserDetailsService userDetailsService() {
        System.out.printf("Password: %s%n", passwordEncoder.encode("password123"));
        UserDetails josep = User.builder()
                .username("josep@tecnocampus.cat")
                .password(passwordEncoder.encode("password123"))
                .roles("USER") // ROLE_USER
                .build();

        UserDetails jordi = User.builder()
                .username("jordi@tecnocampus.cat")
                .password(passwordEncoder.encode("password123"))
                .roles("ADMIN") // ROLE_ADMIN
                .build();

        UserDetails tom = User.builder()
                .username("maria@tecnocampus.cat")
                .password(passwordEncoder.encode("password123"))
                .roles("USER", "ADMIN") // ROLE_ADMIN
                .build();

        return new InMemoryUserDetailsManager(josep, jordi, tom);
    }
}
