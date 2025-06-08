package pe.edu.utp.devminds.utp_task.dto;

import lombok.Data;
import pe.edu.utp.devminds.utp_task.entities.Audit;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class PointsDTO {

    private UUID id;
    private UUID userId;
    private int value;
    private LocalDateTime awardedAt;
    private Audit audit;

}

