package pe.edu.utp.devminds.utp_task.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.utp.devminds.utp_task.dto.GroupDTO;
import pe.edu.utp.devminds.utp_task.entities.Group;
import pe.edu.utp.devminds.utp_task.entities.User;

import java.util.List;
import java.util.UUID;

@Repository
public interface GroupRepository extends JpaRepository<Group, UUID> {

    List<Group> findByCreator(User creator);

    List<Group> findByMembers(User user);  // Buscar grupos donde el usuario es miembro

}

