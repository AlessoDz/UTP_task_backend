package pe.edu.utp.devminds.utp_task.services;

import pe.edu.utp.devminds.utp_task.dto.AcademicCycleDTO;

import java.util.List;
import java.util.UUID;

public interface AcademicCycleService {

    AcademicCycleDTO createAcademicCycle(AcademicCycleDTO academicCycleDTO);

    List<AcademicCycleDTO> getAllAcademicCycles();

    AcademicCycleDTO getAcademicCycleById(UUID id);

    void deleteAcademicCycle(UUID id);

}

