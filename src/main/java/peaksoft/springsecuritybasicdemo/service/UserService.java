package peaksoft.springsecuritybasicdemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import peaksoft.springsecuritybasicdemo.exceptions.ForbiddenException;
import peaksoft.springsecuritybasicdemo.model.Role;
import peaksoft.springsecuritybasicdemo.model.User;
import peaksoft.springsecuritybasicdemo.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        String authEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(authEmail).orElseThrow(
                () -> new UsernameNotFoundException("User with email " + authEmail + " not found"));

        if (!user.getRole().equals(Role.ADMIN)) {
            throw new ForbiddenException("You are not an admin");
        }
        return userRepository.findByIdOrElseThrow(id);
    }


    public void update(Long id, User user) {
        userRepository.findByIdOrElseThrow(id);
    }

}
