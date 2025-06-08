package pe.edu.utp.devminds.utp_task.dto;

import lombok.Getter;
import lombok.Setter;
import pe.edu.utp.devminds.utp_task.entities.Audit;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class MedalDTO {

    private UUID id;
    private UUID userId;
    private UUID academicCycleId;
    private String type;  // Tipo de medalla (Rubi, Platino, Oro, Plata, Bronce)
    private int pointsRequired;
    private LocalDateTime awardedAt;
    private Audit audit;

    public MedalDTO() {
    }

    // Constructor para crear MedalDTO con parámetros específicos
    public MedalDTO(UUID userId, UUID academicCycleId, String type, int pointsRequired, LocalDateTime awardedAt) {
        this.userId = userId;
        this.academicCycleId = academicCycleId;
        this.type = type;
        this.pointsRequired = pointsRequired;
        this.awardedAt = awardedAt;
    }

}

