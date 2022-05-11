package nl.bd.sdbackendopdracht.security.annotations;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@PreAuthorize("hasAuthority(T(nl.bd.sdbackendopdracht.security.enums.RoleEnums).DEVELOPER) ")
public @interface DeveloperAuthorisation {
}
