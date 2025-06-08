package pe.edu.utp.devminds.utp_task.services;

import pe.edu.utp.devminds.utp_task.dto.MedalDTO;

import java.util.List;
import java.util.UUID;

public interface MedalService {

    MedalDTO createMedal(MedalDTO medalDTO);

    List<MedalDTO> getMedalsByUser(UUID userId);

    List<MedalDTO> getMedalsByAcademicCycle(UUID academicCycleId);

    void checkAndAssignMedal(UUID userId, UUID academicCycleId, int points);

    MedalDTO getUserProgress(UUID userId, UUID academicCycleId);

}
