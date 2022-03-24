package nl.bd.sdbackendopdracht.enums;

import org.springframework.security.core.GrantedAuthority;

public enum RoleEnums implements GrantedAuthority {
    ROLE_STUDENT, ROLE_ADMINISTRATOR, ROLE_TEACHER, ROLE_DEVELOPER;

    @Override
    public String getAuthority() {
        return name();
    }

}
