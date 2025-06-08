package pe.edu.utp.devminds.utp_task.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pe.edu.utp.devminds.utp_task.dto.GroupDTO;
import pe.edu.utp.devminds.utp_task.entities.Group;
import pe.edu.utp.devminds.utp_task.entities.User;
import pe.edu.utp.devminds.utp_task.mappers.GroupMapper;
import pe.edu.utp.devminds.utp_task.repositories.GroupRepository;
import pe.edu.utp.devminds.utp_task.repositories.UserRepository;
import pe.edu.utp.devminds.utp_task.services.GroupService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private FileUploadServiceImpl fileUploadService;

    public GroupDTO createGroup(GroupDTO groupDTO, MultipartFile photo) {
        // Lógica para la creación del grupo
        Group group = new Group();
        group.setName(groupDTO.getName());
        group.setDescription(groupDTO.getDescription());

        // Asignar creatorId como un objeto User
        User creator = userRepository.findById(groupDTO.getCreatorId())  // Buscar el usuario por el ID
                .orElseThrow(() -> new IllegalArgumentException("El creador del grupo no existe"));

        group.setCreator(creator);  // Asignar el objeto User al campo creator

        // Asignar la foto al grupo
        if (photo != null && !photo.isEmpty()) {
            String photoUrl = fileUploadService.storeImage(photo);  // Guardamos la imagen
            group.setPhoto(photoUrl);  // Establecemos la URL de la foto
        } else {
            group.setPhoto("/uploads/group-image.png");  // Foto por defecto
        }

        // Guardamos el grupo
        Group savedGroup = groupRepository.save(group);
        return groupMapper.toDTO(savedGroup);  // Convertimos la entidad de grupo a DTO
    }

    @Override
    public List<GroupDTO> getAllGroups() {
        return groupRepository.findAll().stream()
                .map(groupMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public GroupDTO getGroupById(UUID id) {
        return groupRepository.findById(id)
                .map(groupMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Grupo no encontrado"));
    }

    @Override
    public void deleteGroup(UUID id) {
        groupRepository.deleteById(id);
    }

    @Override
    public void addMemberToGroup(UUID groupId, String memberCode) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Grupo no encontrado"));

        User member = userRepository.findByCode(memberCode)
                .orElseThrow(() -> new RuntimeException("Miembro no encontrado"));

        group.getMembers().add(member);
        groupRepository.save(group);
    }

    @Override
    public List<GroupDTO> getGroupsByUserCode(String userCode) {
        User user = userRepository.findByCode(userCode)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        List<Group> groups = groupRepository.findByMembers(user);

        return groups.stream()
                .map(groupMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void removeMemberFromGroup(UUID groupId, String memberCode) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Grupo no encontrado"));

        User member = userRepository.findByCode(memberCode)
                .orElseThrow(() -> new RuntimeException("Miembro no encontrado"));

        if (group.getCreator().getId().equals(member.getId())) {
            throw new RuntimeException("El creador del grupo no puede abandonar el grupo");
        }

        group.getMembers().remove(member);

        groupRepository.save(group);
    }

}
