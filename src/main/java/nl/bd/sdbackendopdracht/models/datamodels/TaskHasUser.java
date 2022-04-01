package nl.bd.sdbackendopdracht.models.datamodels;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class TaskHasUser {
    @Id
    private Long taskContainerId;

    @ManyToOne
    private Student studentHasTask;
    @ManyToOne
    private Task taskHasUser;


}
