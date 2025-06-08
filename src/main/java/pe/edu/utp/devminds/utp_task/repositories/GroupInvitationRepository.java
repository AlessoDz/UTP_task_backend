package pe.edu.utp.devminds.utp_task.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.utp.devminds.utp_task.entities.Group;
import pe.edu.utp.devminds.utp_task.entities.GroupInvitation;
import pe.edu.utp.devminds.utp_task.entities.InvitationStatus;
import pe.edu.utp.devminds.utp_task.entities.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GroupInvitationRepository extends JpaRepository<GroupInvitation, UUID> {

    List<GroupInvitation> findByUserAndStatus(User user, InvitationStatus status);

    List<GroupInvitation> findByGroupAndStatus(Group group, InvitationStatus status);

    Optional<GroupInvitation> findByGroupAndUserAndStatus(Group group, User user, InvitationStatus status);

}