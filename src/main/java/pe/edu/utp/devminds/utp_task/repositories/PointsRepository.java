package pe.edu.utp.devminds.utp_task.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.utp.devminds.utp_task.entities.AcademicCycle;
import pe.edu.utp.devminds.utp_task.entities.Points;
import pe.edu.utp.devminds.utp_task.entities.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface PointsRepository extends JpaRepository<Points, UUID> {

    List<Points> findByUser(User user);

    List<Points> findByAwardedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

}
