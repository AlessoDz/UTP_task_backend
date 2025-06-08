package pe.edu.utp.devminds.utp_task.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.utp.devminds.utp_task.dto.PointsDTO;
import pe.edu.utp.devminds.utp_task.entities.AcademicCycle;
import pe.edu.utp.devminds.utp_task.entities.Points;
import pe.edu.utp.devminds.utp_task.entities.User;
import pe.edu.utp.devminds.utp_task.mappers.PointsMapper;
import pe.edu.utp.devminds.utp_task.repositories.PointsRepository;
import pe.edu.utp.devminds.utp_task.services.PointsService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PointsServiceImpl implements PointsService {

    @Autowired
    private PointsRepository pointsRepository;

    @Autowired
    private PointsMapper pointsMapper;

    @Override
    public PointsDTO createPoints(PointsDTO pointsDTO) {
        Points points = pointsMapper.toEntity(pointsDTO);
        points.setAwardedAt(LocalDateTime.now());
        points = pointsRepository.save(points);
        return pointsMapper.toDTO(points);
    }

    @Override
    public List<PointsDTO> getPointsByUser(UUID userId) {
        User user = new User();
        user.setId(userId);
        List<Points> pointsList = pointsRepository.findByUser(user);
        return pointsList.stream()
                .map(pointsMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PointsDTO> getPointsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        List<Points> pointsList = pointsRepository.findByAwardedAtBetween(startDate, endDate);
        return pointsList.stream()
                .map(pointsMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void addPoints(Points points) {
        points.setAwardedAt(LocalDateTime.now());  // Asignamos la fecha actual al otorgar los puntos
        pointsRepository.save(points);
    }

    // Obtener los puntos totales de un usuario dentro de un ciclo académico
    @Override
    public int getTotalPointsForUserInCycle(UUID userId, UUID academicCycleId) {
        // Encontrar el usuario por ID
        User user = new User();
        user.setId(userId);

        // Encontrar el ciclo académico por ID
        AcademicCycle academicCycle = new AcademicCycle();
        academicCycle.setId(academicCycleId);

        // Obtener los puntos del usuario
        List<Points> points = pointsRepository.findByUser(user);

        // Filtrar los puntos por ciclo académico (según el rango de fechas del ciclo)
        return points.stream()
                .filter(p -> p.getAwardedAt().toLocalDate().isAfter(academicCycle.getStartDate()) &&
                        p.getAwardedAt().toLocalDate().isBefore(academicCycle.getEndDate()))
                .mapToInt(Points::getValue)
                .sum();
    }
}
