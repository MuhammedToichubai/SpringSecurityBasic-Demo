package peaksoft.springsecuritybasicdemo.apis;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.springsecuritybasicdemo.dto.PaginationResponse;
import peaksoft.springsecuritybasicdemo.model.User;
import peaksoft.springsecuritybasicdemo.service.UserService;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserAPI {
    private final UserService userservice;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<PaginationResponse<?>> getUsers(@RequestParam(defaultValue = "1") int pageNumber,
                                                          @RequestParam(defaultValue = "3") int pageSize) {
        return ResponseEntity.ok(userservice.findAll(pageNumber, pageSize));
    }

    @PermitAll
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userservice.findById(id);
    }
}
