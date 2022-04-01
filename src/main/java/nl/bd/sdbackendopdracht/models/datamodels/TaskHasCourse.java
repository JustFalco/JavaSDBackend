package nl.bd.sdbackendopdracht.models.datamodels;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class TaskHasCourse {

    @Id
    private Long taskCourseContainerId;
    @ManyToOne
    private Task taskHasCourse;
    @ManyToOne
    private Course courseHasTask;
}
