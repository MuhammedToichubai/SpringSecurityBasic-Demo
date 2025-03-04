package peaksoft.springsecuritybasicdemo.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN,
    INSTRUCTOR,
    STUDENT,
    USER;

    @Override
    public String getAuthority() {
        return name();
    }
}
