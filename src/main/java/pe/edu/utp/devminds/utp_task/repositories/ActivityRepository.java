package pe.edu.utp.devminds.utp_task.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.utp.devminds.utp_task.entities.AcademicCycle;
import pe.edu.utp.devminds.utp_task.entities.Activity;
import pe.edu.utp.devminds.utp_task.entities.ActivityType;

import java.util.List;
import java.util.UUID;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, UUID> {

    List<Activity> findAllByAcademicCycle(AcademicCycle academicCycle);

    List<Activity> findByType(ActivityType type);

}
