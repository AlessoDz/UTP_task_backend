package pe.edu.utp.devminds.utp_task.mappers;

import org.springframework.stereotype.Component;
import pe.edu.utp.devminds.utp_task.dto.UserDTO;
import pe.edu.utp.devminds.utp_task.entities.Role;
import pe.edu.utp.devminds.utp_task.entities.User;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setLastname(user.getLastname());
        dto.setCode(user.getCode());
        dto.setPassword(user.getPassword());
        dto.setPhone(user.getPhone());
        dto.setProfilePicture(user.getProfilePicture());

        if (user.getRoles() != null) {
            Set<String> roleNames = user.getRoles().stream()
                    .map(Role::getName)
                    .collect(Collectors.toSet());
            dto.setRoles(roleNames);
        }

        dto.setAudit(user.getAudit());
        return dto;
    }

    public User toEntity(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setLastname(dto.getLastname());
        user.setCode(dto.getCode());
        user.setPassword(dto.getPassword());
        user.setPhone(dto.getPhone());
        user.setProfilePicture(dto.getProfilePicture());
        return user;
    }

}
