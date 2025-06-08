package pe.edu.utp.devminds.utp_task.services;

import pe.edu.utp.devminds.utp_task.dto.StreakDTO;
import pe.edu.utp.devminds.utp_task.entities.User;

import java.util.UUID;

public interface StreakService {

    // Activar la racha del usuario
    void activateStreak(User user);

    // Obtener la racha más larga
    StreakDTO getLongestStreak(UUID userId);

    // Obtener la racha actual
    StreakDTO getCurrentStreak(UUID userId);

    // Resetear la racha
    void resetStreak(UUID userId);

}

