package peaksoft.springsecuritybasicdemo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import peaksoft.springsecuritybasicdemo.model.Role;
import peaksoft.springsecuritybasicdemo.model.User;

import java.util.List;


@Getter
@Setter
@Builder
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private Role role;


    public static UserResponse entityToDto(User user) {
      return UserResponse.builder()
               .id(user.getId())
               .name(user.getName())
               .email(user.getEmail())
               .role(user.getRole())
               .build();
    }

    public static List<UserResponse> entitiesToDto(List<User> users) {
       return users.stream().map(UserResponse::entityToDto).toList();
    }
}
