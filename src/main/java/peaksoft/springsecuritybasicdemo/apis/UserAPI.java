package peaksoft.springsecuritybasicdemo.apis;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import peaksoft.springsecuritybasicdemo.model.User;
import peaksoft.springsecuritybasicdemo.service.UserService;

import java.util.List;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserAPI {
    private final UserService userservice;


    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userservice.findAll());
    }

    @PermitAll
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userservice.findById(id);
    }
}
