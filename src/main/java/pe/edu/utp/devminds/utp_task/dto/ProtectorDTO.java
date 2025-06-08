package pe.edu.utp.devminds.utp_task.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ProtectorDTO {

    private UUID id;
    private UUID userId;
    private UUID streakId;
    private LocalDateTime activatedAt;

}
