package pe.edu.utp.devminds.utp_task.dto;

import lombok.Data;
import pe.edu.utp.devminds.utp_task.entities.Audit;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
public class TaskDTO {

    private UUID id;
    private String title;
    private String description;
    private String priority;
    private String status;
    private UUID creatorId;  // ID del creador
    private UUID groupId;  // ID del grupo (si es grupal)
    private Set<UUID> subtaskIds;  // IDs de las subtareas
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean isRepeating;
    private String repeatFrequency;
    private String repeatDays;
    private boolean reminderWsp;
    private String repeatHandlingOption;  // Nuevo campo para manejar opciones de fecha (31, 30, 29)

    private Audit audit;

}
