package nl.bd.sdbackendopdracht.models.datamodels;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "schools")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class School {
    @Id
    @SequenceGenerator(
            name = "school_sequencegenerator",
            sequenceName = "school_sequencegenerator",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "school_sequencegenerator",
            strategy = GenerationType.SEQUENCE
    )
    private Long schoolId;
    private String schoolName;
    private String schoolMail;
}
