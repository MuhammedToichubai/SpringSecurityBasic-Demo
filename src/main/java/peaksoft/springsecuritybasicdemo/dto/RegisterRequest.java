package peaksoft.springsecuritybasicdemo.dto;

import peaksoft.springsecuritybasicdemo.model.Role;

public record RegisterRequest(
        String email,
        String password,
        String name,
        Role role
) {
}
