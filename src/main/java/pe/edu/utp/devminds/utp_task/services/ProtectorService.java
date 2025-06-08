package pe.edu.utp.devminds.utp_task.services;

import pe.edu.utp.devminds.utp_task.dto.ProtectorDTO;

import java.util.List;
import java.util.UUID;

public interface ProtectorService {

    // Crear un protector
    ProtectorDTO createProtector(UUID userId, UUID streakId);

    // Obtener protectores de una racha
    List<ProtectorDTO> getProtectorsByStreak(UUID streakId);

    // Gastar un protector
    void useProtector(UUID streakId);

}
