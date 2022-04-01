package nl.bd.sdbackendopdracht.security.enums;

import com.google.common.collect.Sets;

import java.util.Set;

import static nl.bd.sdbackendopdracht.security.enums.UserPermissions.STUDENT_READ;
import static nl.bd.sdbackendopdracht.security.enums.UserPermissions.STUDENT_WRITE;

public enum RoleEnums {
    STUDENT(Sets.newHashSet(STUDENT_READ)),
    ADMINISTRATOR(Sets.newHashSet()),
    TEACHER(Sets.newHashSet()),
    DEVELOPER(Sets.newHashSet(STUDENT_READ, STUDENT_WRITE));

    private final Set<UserPermissions> permissions;

    RoleEnums(Set<UserPermissions> permissions) {
        this.permissions = permissions;
    }
}
