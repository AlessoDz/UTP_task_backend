package pe.edu.utp.devminds.utp_task.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.utp.devminds.utp_task.entities.Audit;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class GroupDTO {

    private UUID id;
    private String name;
    private UUID creatorId;
    private String description;  // Descripción del grupo
    private String photo;  // URL de la foto del grupo
    private Set<UUID> memberIds = new HashSet<>();
    private Set<UUID> fileIds = new HashSet<>();
    private Set<UUID> commentIds = new HashSet<>();
    private Set<UUID> taskIds = new HashSet<>();
    private Audit audit;

}
