package pe.edu.utp.devminds.utp_task.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.utp.devminds.utp_task.entities.Protector;
import pe.edu.utp.devminds.utp_task.entities.Streak;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProtectorRepository extends JpaRepository<Protector, UUID> {

    List<Protector> findByStreak(Streak streak);

}
