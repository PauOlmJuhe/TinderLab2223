package cat.tecnocampus.tinder.configuration.security;

import cat.tecnocampus.tinder.configuration.security.jwt.JwtAuthenticationFilter;
import cat.tecnocampus.tinder.configuration.security.jwt.JwtConfig;
import cat.tecnocampus.tinder.configuration.security.jwt.JwtTokenVerifierFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ApplicationSecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final TinderUserDetailsService tinderUserDetailsService;
    private final JwtConfig jwtConfig;

    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, TinderUserDetailsService tinderUserDetailsService,
                                     JwtConfig jwtConfig) {
        this.passwordEncoder = passwordEncoder;
        this.tinderUserDetailsService = tinderUserDetailsService;
        this.jwtConfig = jwtConfig;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                .antMatchers( "/", "index", "/css/*", "/js/*", "/*.html", "/h2-console/**").permitAll()
                .antMatchers("/quotes/**").permitAll()
                .antMatchers("/profiles/me/**").hasRole("USER")
                .antMatchers("/profiles/**", "/profilesByName/**").hasRole("ADMIN")
                .anyRequest()
                .authenticated()

                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationProvider(), jwtConfig))
                .addFilterAfter(new JwtTokenVerifierFilter(jwtConfig), JwtAuthenticationFilter.class)

                .csrf().disable()
                .cors().and()
                .headers().frameOptions().disable().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return httpSecurity.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(tinderUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);

        return authProvider;
    }
}
