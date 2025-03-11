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
import peaksoft.springsecuritybasicdemo.config.jwt.JwtService;
import peaksoft.springsecuritybasicdemo.dto.AuthResponse;
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
    private final JwtService jwtService;

    public ResponseEntity<?> registration(RegisterRequest request) {
        User user = new User();
        user.setEmail(request.email());
        user.setRole(request.role());
        user.setName(request.name());
        String encodePassword = passwordEncoder.encode(request.password());
        user.setPassword(encodePassword);
        try {
            User userByEmail = userRepository.findByEmail(request.email()).orElse(null);
            if (userByEmail != null) {
                throw  new BadCredentialsException("Email already in use");
            }
            User savedUser = userRepository.save(user);
            String generatedToken = jwtService.generateToken(savedUser);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(AuthResponse.builder()
                            .token(generatedToken)
                            .email(savedUser.getEmail())
                            .role(savedUser.getRole())
                            .build());

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


            User user = userRepository.findByEmail(request.email())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            boolean matches = passwordEncoder.matches(request.password(), user.getPassword());

            if (!matches) {
                throw new BadCredentialsException("Incorrect password");
            }
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(AuthResponse.builder()
                            .token(jwtService.generateToken(user))
                            .email(user.getEmail())
                            .role(user.getRole())
                            .build());

        }catch (BadCredentialsException | UsernameNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", e.getMessage()));
        }

    }
}
