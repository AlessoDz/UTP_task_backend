package pe.edu.utp.devminds.utp_task.mappers;

import org.springframework.stereotype.Component;
import pe.edu.utp.devminds.utp_task.dto.GroupDTO;
import pe.edu.utp.devminds.utp_task.entities.*;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class GroupMapper {

    public GroupDTO toDTO(Group group) {
        GroupDTO dto = new GroupDTO();
        dto.setId(group.getId());
        dto.setName(group.getName());
        dto.setDescription(group.getDescription());
        dto.setPhoto(group.getPhoto());
        dto.setCreatorId(group.getCreator().getId());

        dto.setMemberIds(group.getMembers().stream()
                .map(User::getId)
                .collect(Collectors.toSet()));

        dto.setFileIds(group.getFiles().stream()
                .map(File::getId)
                .collect(Collectors.toSet()));

        dto.setCommentIds(group.getComments().stream()
                .map(Comment::getId)
                .collect(Collectors.toSet()));

        dto.setTaskIds(group.getTasks().stream()
                .map(Task::getId)
                .collect(Collectors.toSet()));

        dto.setAudit(group.getAudit());
        return dto;
    }

    public Group toEntity(GroupDTO dto) {
        Group group = new Group();
        group.setId(dto.getId());
        group.setName(dto.getName());
        group.setDescription(dto.getDescription());
        group.setPhoto(dto.getPhoto());

        // Convertir creatorId a User
        User creator = new User();
        creator.setId(dto.getCreatorId());  // Asignamos el creatorId al objeto User
        group.setCreator(creator);  // Asignamos el User al campo creator

        Set<User> members = dto.getMemberIds().stream()
                .map(id -> {
                    User user = new User();
                    user.setId(id);
                    return user;
                })
                .collect(Collectors.toSet());
        group.setMembers(members);

        return group;
    }
}
