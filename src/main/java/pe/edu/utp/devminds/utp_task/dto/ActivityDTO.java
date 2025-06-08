package pe.edu.utp.devminds.utp_task.dto;

import lombok.Data;
import pe.edu.utp.devminds.utp_task.entities.ActivityType;
import pe.edu.utp.devminds.utp_task.entities.Audit;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class ActivityDTO {

    private UUID id;
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean hasAlarm;
    private String location;
    private String link;
    private String instructor;
    private ActivityType type;
    private String repeatDays;
    private boolean isRemote;
    private UUID academicCycleId;  // El ciclo académico al que pertenece la actividad
    private Set<UUID> participantsIds = new HashSet<>();  // IDs de los participantes

    private Audit audit;  // Auditoría (se incluirá en el DTO)
}
