package peaksoft.springsecuritybasicdemo.dto;


import lombok.Builder;
import peaksoft.springsecuritybasicdemo.model.Role;

@Builder
public record AuthResponse(
        String token,
        String email,
        Role role
) { }
