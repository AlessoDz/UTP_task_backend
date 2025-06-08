package pe.edu.utp.devminds.utp_task.services;

import pe.edu.utp.devminds.utp_task.dto.PointsDTO;
import pe.edu.utp.devminds.utp_task.entities.AcademicCycle;
import pe.edu.utp.devminds.utp_task.entities.Points;
import pe.edu.utp.devminds.utp_task.entities.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface PointsService {

    PointsDTO createPoints(PointsDTO pointsDTO);

    List<PointsDTO> getPointsByUser(UUID userId);

    List<PointsDTO> getPointsByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    void addPoints(Points points);

    int getTotalPointsForUserInCycle(UUID user, UUID academicCycle);


}

