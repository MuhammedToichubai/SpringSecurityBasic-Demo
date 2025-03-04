package peaksoft.springsecuritybasicdemo.apis;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import peaksoft.springsecuritybasicdemo.dto.LoginRequest;
import peaksoft.springsecuritybasicdemo.dto.RegisterRequest;
import peaksoft.springsecuritybasicdemo.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthAPI {
    private final AuthService authService;

    // sign-in
    @PostMapping("/sign-in")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
       return authService.login(request);
    }

    //sign-up
    @PostMapping("/sign-up")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        return authService.registration(request);
    }
}
