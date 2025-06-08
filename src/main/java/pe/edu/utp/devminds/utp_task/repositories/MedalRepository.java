package pe.edu.utp.devminds.utp_task.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.utp.devminds.utp_task.entities.AcademicCycle;
import pe.edu.utp.devminds.utp_task.entities.Medal;
import pe.edu.utp.devminds.utp_task.entities.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MedalRepository extends JpaRepository<Medal, UUID> {

    // Buscar medallas por usuario
    List<Medal> findByUser(User user);

    // Buscar medallas por ciclo académico
    List<Medal> findByAcademicCycle(AcademicCycle academicCycle);

    // Buscar medallas por usuario y ciclo académico
    Optional<Medal> findByUserAndAcademicCycle(User user, AcademicCycle academicCycle);

}
