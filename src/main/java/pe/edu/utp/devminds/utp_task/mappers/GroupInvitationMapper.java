package pe.edu.utp.devminds.utp_task.mappers;

import org.springframework.stereotype.Component;
import pe.edu.utp.devminds.utp_task.dto.GroupInvitationDTO;
import pe.edu.utp.devminds.utp_task.entities.Group;
import pe.edu.utp.devminds.utp_task.entities.GroupInvitation;
import pe.edu.utp.devminds.utp_task.entities.User;

@Component
public class GroupInvitationMapper {

    public GroupInvitationDTO toDTO(GroupInvitation invitation) {
        GroupInvitationDTO dto = new GroupInvitationDTO();
        dto.setId(invitation.getId());
        dto.setGroupId(invitation.getGroup().getId());
        dto.setUserId(invitation.getUser().getId());
        dto.setStatus(invitation.getStatus());
        dto.setAudit(invitation.getAudit());
        dto.setExpiryDate(invitation.getExpiryDate());
        dto.setGroupName(invitation.getGroup().getName());
        dto.setInviterName(invitation.getGroup().getCreator().getName());
        return dto;
    }

    public GroupInvitation toEntity(GroupInvitationDTO dto) {
        GroupInvitation invitation = new GroupInvitation();
        invitation.setId(dto.getId());
        Group group = new Group();
        group.setId(dto.getGroupId());
        invitation.setGroup(group);
        User user = new User();
        user.setId(dto.getUserId());
        invitation.setUser(user);
        invitation.setStatus(dto.getStatus());
        invitation.setExpiryDate(dto.getExpiryDate());
        return invitation;
    }
}


