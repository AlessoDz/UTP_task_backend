package pe.edu.utp.devminds.utp_task.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Data
public class StreakDTO {

    private UUID id;
    private UUID userId;
    private int currentStreak;
    private int maxStreak;
    private LocalDate lastCompletedAt;
    private Set<UUID> protectorIds;

}

