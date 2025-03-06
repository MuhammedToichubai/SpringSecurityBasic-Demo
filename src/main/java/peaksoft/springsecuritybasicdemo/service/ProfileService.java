package peaksoft.springsecuritybasicdemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.springsecuritybasicdemo.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final UserRepository userRepository;

    public void getProfile(Long id) {
        userRepository.findByIdOrElseThrow(id);
    }
}
