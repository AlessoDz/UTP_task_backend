package pe.edu.utp.devminds.utp_task.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.utp.devminds.utp_task.entities.AcademicCycle;

import java.util.UUID;

@Repository
public interface AcademicCycleRepository extends JpaRepository<AcademicCycle, UUID> {

    AcademicCycle findByName(String name);

}
