package peaksoft.springsecuritybasicdemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.springsecuritybasicdemo.dto.LoginRequest;
import peaksoft.springsecuritybasicdemo.dto.RegisterRequest;
import peaksoft.springsecuritybasicdemo.model.Role;
import peaksoft.springsecuritybasicdemo.model.User;
import peaksoft.springsecuritybasicdemo.repositories.UserRepository;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<?> registration(RegisterRequest request) {
        User user = new User();
        user.setEmail(request.email());
        user.setRole(request.role());
        user.setName(request.name());
        String encodePassword = passwordEncoder.encode(request.password());
        user.setPassword(encodePassword);
//        user.setRole(Role.USER);
        try {
            User userByEmail = userRepository.findByEmail(request.email()).orElse(null);
            if (userByEmail != null) {
                throw  new BadCredentialsException("Email already in use");
            }

            return ResponseEntity.ok(userRepository.save(user));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));
        }
    }

    public ResponseEntity<?> login(LoginRequest request) {
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email(),
                            request.password())

            );
            System.out.println("authenticate.getName() = " + authenticate.getName());

            User user = userRepository.findByEmail(request.email())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            boolean matches = passwordEncoder.matches(request.password(), user.getPassword());

            if (!matches) {
                throw new BadCredentialsException("Incorrect password");
            }
            return ResponseEntity.ok(Map.of("message", "Welcome " + user.getName()));

        }catch (BadCredentialsException | UsernameNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", e.getMessage()));
        }

    }
}
