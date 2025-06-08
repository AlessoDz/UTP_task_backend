package pe.edu.utp.devminds.utp_task.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pe.edu.utp.devminds.utp_task.dto.GroupDTO;
import pe.edu.utp.devminds.utp_task.services.FileUploadService;
import pe.edu.utp.devminds.utp_task.services.GroupService;
import pe.edu.utp.devminds.utp_task.utils.ResponseUtil;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createGroup(
            @RequestPart("groupDTO") String groupJson,
            @RequestPart(value = "photo", required = false) MultipartFile photo
    ) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        GroupDTO groupDTO = objectMapper.readValue(groupJson, GroupDTO.class);

        try {
            GroupDTO createdGroup = groupService.createGroup(groupDTO, photo);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ResponseUtil.successResponse("Grupo creado exitosamente", createdGroup));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseUtil.errorResponse(ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtil.errorResponse("Error al crear el grupo"));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllGroups() {
        try {
            List<GroupDTO> groups = groupService.getAllGroups();
            return ResponseEntity.ok(ResponseUtil.successResponse("Grupos encontrados exitosamente", groups));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseUtil.errorResponse("Error al buscar los grupos"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGroupById(@PathVariable UUID id) {
        try {
            GroupDTO group = groupService.getGroupById(id);
            return ResponseEntity.ok(ResponseUtil.successResponse("Grupo encontrado exitosamente", group));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseUtil.errorResponse(ex.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable UUID id) {
        try {
            groupService.deleteGroup(id);
            return ResponseEntity.ok(ResponseUtil.successResponse("Grupo eliminado exitosamente", null));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseUtil.errorResponse(ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtil.errorResponse("Error al eliminar el grupo"));
        }
    }

    @PostMapping("/{groupId}/assign-member/{memberCode}")
    public ResponseEntity<?> addMemberToGroup(
            @PathVariable UUID groupId,
            @PathVariable String memberCode) {
        try {
            groupService.addMemberToGroup(groupId, memberCode);
            return ResponseEntity.ok(ResponseUtil.successResponse("Miembro asignado al grupo exitosamente", null));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseUtil.errorResponse(ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtil.errorResponse("Error al asignar miembro al grupo"));
        }
    }

    @GetMapping("/user/{userCode}/groups")
    public ResponseEntity<?> getGroupsByUserCode(@PathVariable String userCode) {
        try {
            List<GroupDTO> groups = groupService.getGroupsByUserCode(userCode);
            return ResponseEntity.ok(ResponseUtil.successResponse("Grupos encontrados exitosamente", groups));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseUtil.errorResponse("Error al buscar los grupos del usuario"));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtil.errorResponse("Error interno al buscar los grupos del usuario"));
        }
    }

    @PostMapping("/{groupId}/remove-member/{memberCode}")
    public ResponseEntity<?> removeMemberFromGroup(
            @PathVariable UUID groupId,
            @PathVariable String memberCode) {
        try {
            groupService.removeMemberFromGroup(groupId, memberCode);
            return ResponseEntity.ok(ResponseUtil.successResponse("Miembro eliminado del grupo exitosamente", null));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseUtil.errorResponse(ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtil.errorResponse("Error al eliminar miembro del grupo"));
        }
    }

}