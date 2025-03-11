package peaksoft.springsecuritybasicdemo.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import peaksoft.springsecuritybasicdemo.config.jwt.JwtFilter;
import peaksoft.springsecuritybasicdemo.config.jwt.JwtService;
import peaksoft.springsecuritybasicdemo.repositories.UserRepository;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
@RequiredArgsConstructor
public class SpringSecurityConfig {
    private final UserRepository userRepository;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, JwtService jwtService) throws Exception {
        http.authorizeHttpRequests(authorize -> {
            authorize
                    .requestMatchers(
                            "/api/auth/sign-in",
                            "/api/auth/sign-up")
                    .permitAll()
                    .requestMatchers("/api/users/").hasAuthority("ADMIN")
                    .anyRequest()
                    .authenticated();
        });
        http.csrf(AbstractHttpConfigurer::disable);
        http.addFilterBefore(new JwtFilter(jwtService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        return daoAuthenticationProvider;
    }

    @Bean
    UserDetailsService userDetailsService() {
        return email ->
                userRepository.findByEmail(email)
                        .orElseThrow(() -> new UsernameNotFoundException(
                                String.format("User with email: %s not found", email)
                        ));
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager() {
        return new ProviderManager(authenticationProvider());
    }

}
