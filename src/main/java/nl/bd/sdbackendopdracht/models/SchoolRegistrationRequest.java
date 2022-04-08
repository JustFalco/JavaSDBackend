package nl.bd.sdbackendopdracht.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class SchoolRegistrationRequest {
    private String schoolName;
    private String schoolMail;
}
