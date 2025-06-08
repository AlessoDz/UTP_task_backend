package pe.edu.utp.devminds.utp_task.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.utp.devminds.utp_task.dto.GroupInvitationDTO;
import pe.edu.utp.devminds.utp_task.entities.Group;
import pe.edu.utp.devminds.utp_task.entities.GroupInvitation;
import pe.edu.utp.devminds.utp_task.entities.InvitationStatus;
import pe.edu.utp.devminds.utp_task.entities.User;
import pe.edu.utp.devminds.utp_task.mappers.GroupInvitationMapper;
import pe.edu.utp.devminds.utp_task.repositories.GroupInvitationRepository;
import pe.edu.utp.devminds.utp_task.repositories.GroupRepository;
import pe.edu.utp.devminds.utp_task.repositories.UserRepository;
import pe.edu.utp.devminds.utp_task.services.GroupInvitationService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GroupInvitationServiceImpl implements GroupInvitationService {

    @Autowired
    private GroupInvitationRepository groupInvitationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupInvitationMapper groupInvitationMapper;

    // Validaciones
    private void validateInvitationNotExists(Group group, User user) {
        groupInvitationRepository.findByGroupAndUserAndStatus(group, user, InvitationStatus.PENDIENTE)
                .ifPresent(invitation -> {
                    throw new RuntimeException("El estudiante ya tiene una invitación pendiente para este grupo");
                });
    }

    private void validateUserAlreadyInGroup(Group group, User user) {
        if (group.getMembers().contains(user)) {
            throw new RuntimeException("El estudiante ya pertenece al grupo");
        }
    }

    private void validateInvitationExpiry(GroupInvitation invitation) {
        if (LocalDateTime.now().isAfter(invitation.getExpiryDate())) {
            invitation.setStatus(InvitationStatus.RECHAZADA);
            groupInvitationRepository.save(invitation);
            throw new RuntimeException("La invitación ha vencido");
        }
    }

    @Override
    public void sendInvitation(UUID groupId, String studentCode) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Grupo no encontrado"));

        User user = userRepository.findByCode(studentCode)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        // Validar si el estudiante ya tiene una invitación pendiente o ya pertenece al grupo
        validateInvitationNotExists(group, user);
        validateUserAlreadyInGroup(group, user);

        // Crear la invitación
        GroupInvitation invitation = new GroupInvitation();
        invitation.setGroup(group);
        invitation.setUser(user);
        invitation.setStatus(InvitationStatus.PENDIENTE);
        invitation.setExpiryDate(LocalDateTime.now().plusDays(7));  // Vence en 7 días

        groupInvitationRepository.save(invitation);
    }

    @Override
    public void acceptInvitation(UUID invitationId) {
        GroupInvitation invitation = groupInvitationRepository.findById(invitationId)
                .orElseThrow(() -> new RuntimeException("Invitación no encontrada"));

        validateInvitationExpiry(invitation);

        // Aceptar la invitación
        invitation.setStatus(InvitationStatus.ACEPTADA);

        // Agregar al estudiante al grupo
        invitation.getGroup().getMembers().add(invitation.getUser());

        groupInvitationRepository.save(invitation);
    }

    @Override
    public void rejectInvitation(UUID invitationId) {
        GroupInvitation invitation = groupInvitationRepository.findById(invitationId)
                .orElseThrow(() -> new RuntimeException("Invitación no encontrada"));

        validateInvitationExpiry(invitation);

        // Rechazar la invitación
        invitation.setStatus(InvitationStatus.RECHAZADA);

        groupInvitationRepository.save(invitation);
    }

    @Override
    public List<GroupInvitationDTO> getPendingInvitations(String studentCode) {
        User user = userRepository.findByCode(studentCode)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        List<GroupInvitation> invitations = groupInvitationRepository.findByUserAndStatus(user, InvitationStatus.PENDIENTE);

        return invitations.stream()
                .map(groupInvitationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<GroupInvitationDTO> getSentInvitations(UUID groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Grupo no encontrado"));

        List<GroupInvitation> invitations = groupInvitationRepository.findByGroupAndStatus(group, InvitationStatus.PENDIENTE);

        return invitations.stream()
                .map(groupInvitationMapper::toDTO)
                .collect(Collectors.toList());
    }
}
