package pe.edu.utp.devminds.utp_task.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class AcademicCycleDTO {

    private UUID id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;

}
