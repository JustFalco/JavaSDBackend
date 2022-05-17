package nl.bd.sdbackendopdracht.security.enums;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum RoleEnums {
    STUDENT(Sets.newHashSet()),
    ADMINISTRATOR(Sets.newHashSet()),
    TEACHER(Sets.newHashSet()),
    DEVELOPER(Sets.newHashSet());

    private final Set<UserPermissions> permissions;

    RoleEnums(Set<UserPermissions> permissions) {
        this.permissions = permissions;
    }

    public Set<UserPermissions> getPermissions() {
        return this.permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return permissions;
    }
}
