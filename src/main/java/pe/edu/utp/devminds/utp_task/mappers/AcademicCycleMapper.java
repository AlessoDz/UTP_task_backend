package pe.edu.utp.devminds.utp_task.mappers;

import org.springframework.stereotype.Component;
import pe.edu.utp.devminds.utp_task.dto.AcademicCycleDTO;
import pe.edu.utp.devminds.utp_task.entities.AcademicCycle;

@Component
public class AcademicCycleMapper {

    // Convierte la entidad a DTO
    public AcademicCycleDTO toDTO(AcademicCycle academicCycle) {
        AcademicCycleDTO dto = new AcademicCycleDTO();
        dto.setId(academicCycle.getId());
        dto.setName(academicCycle.getName());
        dto.setStartDate(academicCycle.getStartDate());
        dto.setEndDate(academicCycle.getEndDate());
        return dto;
    }

    // Convierte el DTO a entidad
    public AcademicCycle toEntity(AcademicCycleDTO dto) {
        AcademicCycle academicCycle = new AcademicCycle();
        academicCycle.setId(dto.getId());
        academicCycle.setName(dto.getName());
        academicCycle.setStartDate(dto.getStartDate());
        academicCycle.setEndDate(dto.getEndDate());
        return academicCycle;
    }

}
