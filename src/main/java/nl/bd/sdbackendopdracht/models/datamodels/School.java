package nl.bd.sdbackendopdracht.models.datamodels;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "schools")
public class School {

    @Id
    private Long Id;
    private String schoolName;

}
