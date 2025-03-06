package peaksoft.springsecuritybasicdemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import peaksoft.springsecuritybasicdemo.dto.PaginationResponse;
import peaksoft.springsecuritybasicdemo.dto.UserResponse;
import peaksoft.springsecuritybasicdemo.exceptions.ForbiddenException;
import peaksoft.springsecuritybasicdemo.model.Role;
import peaksoft.springsecuritybasicdemo.model.User;
import peaksoft.springsecuritybasicdemo.repositories.UserRepository;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public PaginationResponse<UserResponse> findAll(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize);

        Page<User> users = userRepository.findAll(pageable);

        var response = new PaginationResponse<UserResponse>();
        response.setPageNumber(users.getNumber()+1);
        response.setPageSize(users.getSize());
        response.setTotalPages(users.getTotalPages());
        response.setTotalElements(users.getTotalElements());
        response.setObjects(UserResponse.entitiesToDto(users.stream().toList()));
        return response;
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
