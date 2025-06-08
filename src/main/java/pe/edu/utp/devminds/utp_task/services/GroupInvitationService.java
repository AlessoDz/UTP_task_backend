package pe.edu.utp.devminds.utp_task.services;

import pe.edu.utp.devminds.utp_task.dto.GroupInvitationDTO;

import java.util.List;
import java.util.UUID;

public interface GroupInvitationService {

    void sendInvitation(UUID groupId, String studentCode);

    void acceptInvitation(UUID invitationId);

    void rejectInvitation(UUID invitationId);

    List<GroupInvitationDTO> getPendingInvitations(String studentCode);

    List<GroupInvitationDTO> getSentInvitations(UUID groupId);

}
