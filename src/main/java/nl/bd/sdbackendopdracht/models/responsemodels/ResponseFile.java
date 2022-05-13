package nl.bd.sdbackendopdracht.models.responsemodels;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseFile {
    private String name;
    private String downloadUri;
    private String type;
    private long size;
}
