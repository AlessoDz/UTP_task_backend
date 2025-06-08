package pe.edu.utp.devminds.utp_task.services;

import org.springframework.web.multipart.MultipartFile;
import pe.edu.utp.devminds.utp_task.dto.GroupDTO;

import java.util.List;
import java.util.UUID;

public interface GroupService {

    GroupDTO createGroup(GroupDTO groupDTO, MultipartFile photo);

    List<GroupDTO> getAllGroups();

    GroupDTO getGroupById(UUID id);

    void deleteGroup(UUID id);

    void addMemberToGroup(UUID groupId, String memberCode);

    List<GroupDTO> getGroupsByUserCode(String userCode);

    void removeMemberFromGroup(UUID groupId, String memberCode);

}
