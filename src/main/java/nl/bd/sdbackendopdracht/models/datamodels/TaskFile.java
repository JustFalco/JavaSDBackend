package nl.bd.sdbackendopdracht.models.datamodels;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "files")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TaskFile {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String fileId;

    private String name;
    private String type;
    @Lob
    private byte[] data;

    @ManyToOne
    private Task fileBelongsToTask;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TaskFile taskFile = (TaskFile) o;
        return fileId != null && Objects.equals(fileId, taskFile.fileId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
