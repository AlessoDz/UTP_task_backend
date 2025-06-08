package pe.edu.utp.devminds.utp_task.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.utp.devminds.utp_task.entities.Group;
import pe.edu.utp.devminds.utp_task.entities.Task;
import pe.edu.utp.devminds.utp_task.entities.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    // Buscar tareas por creador (usuario)
    List<Task> findByCreator(User creator);

    // Buscar tareas de un grupo
    List<Task> findByGroup(Group group);

    // Buscar tareas por repetición (diarias, semanales, mensuales)
    List<Task> findByRepeatFrequency(String repeatFrequency);

    // Buscar tareas con fecha de inicio y fin
    List<Task> findByStartDateBetween(LocalDateTime startDate, LocalDateTime endDate);

}
