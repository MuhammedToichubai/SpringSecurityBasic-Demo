package peaksoft.springsecuritybasicdemo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import peaksoft.springsecuritybasicdemo.exceptions.BadRequestException;
import peaksoft.springsecuritybasicdemo.exceptions.NotFoundException;
import peaksoft.springsecuritybasicdemo.model.User;

import java.util.NoSuchElementException;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    default User findByIdOrElseThrow(Long id){
       return findById(id).orElseThrow(
                () -> new BadRequestException(String.format("User with id %s not found", id)));
    };
}
