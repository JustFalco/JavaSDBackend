package nl.bd.sdbackendopdracht.security.enums;

import lombok.Getter;

@Getter
public enum UserPermissions {
    STUDENT_READ("student:read"),
    STUDENT_WRITE("student:write");

    private final String permission;

    UserPermissions(String permission) {
        this.permission = permission;
    }
}
