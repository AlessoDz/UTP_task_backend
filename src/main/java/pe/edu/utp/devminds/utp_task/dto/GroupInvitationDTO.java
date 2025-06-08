package pe.edu.utp.devminds.utp_task.dto;

import lombok.Data;
import pe.edu.utp.devminds.utp_task.entities.Audit;
import pe.edu.utp.devminds.utp_task.entities.InvitationStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class GroupInvitationDTO {

    private UUID id;
    private UUID groupId;
    private String groupName;
    private String inviterName;
    private UUID userId;
    private InvitationStatus status;
    private LocalDateTime expiryDate;
    private Audit audit;

}
