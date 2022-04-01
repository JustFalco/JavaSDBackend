package nl.bd.sdbackendopdracht.models.datamodels;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class CourseHasStudent {
    @Id
    private Long courseHasStudentId;
    @ManyToOne
    private Student studentHasCourse;
    @ManyToOne
    private Course courseHasStudent;
}
