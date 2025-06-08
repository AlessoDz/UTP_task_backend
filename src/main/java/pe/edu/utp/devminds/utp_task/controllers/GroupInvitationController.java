package pe.edu.utp.devminds.utp_task.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.devminds.utp_task.dto.GroupInvitationDTO;
import pe.edu.utp.devminds.utp_task.services.GroupInvitationService;
import pe.edu.utp.devminds.utp_task.utils.ResponseUtil;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/group-invitations")
public class GroupInvitationController {

    private final GroupInvitationService groupInvitationService;

    public GroupInvitationController(GroupInvitationService groupInvitationService) {
        this.groupInvitationService = groupInvitationService;
    }

    @PostMapping("/send")
    public ResponseEntity<?> sendInvitation(@RequestParam UUID groupId, @RequestParam String studentCode) {
        try {
            groupInvitationService.sendInvitation(groupId, studentCode);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ResponseUtil.successResponse("Invitación enviada exitosamente", null));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseUtil.errorResponse(ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtil.errorResponse("Error al enviar la invitación"));
        }
    }

    @PostMapping("/accept/{invitationId}")
    public ResponseEntity<?> acceptInvitation(@PathVariable UUID invitationId) {
        try {
            groupInvitationService.acceptInvitation(invitationId);
            return ResponseEntity.ok(ResponseUtil.successResponse("Invitación aceptada exitosamente", null));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseUtil.errorResponse(ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtil.errorResponse("Error al aceptar la invitación"));
        }
    }

    @PostMapping("/reject/{invitationId}")
    public ResponseEntity<?> rejectInvitation(@PathVariable UUID invitationId) {
        try {
            groupInvitationService.rejectInvitation(invitationId);
            return ResponseEntity.ok(ResponseUtil.successResponse("Invitación rechazada exitosamente", null));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseUtil.errorResponse(ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtil.errorResponse("Error al rechazar la invitación"));
        }
    }

    @GetMapping("/pending/{studentCode}")
    public ResponseEntity<?> getPendingInvitations(@PathVariable String studentCode) {
        try {
            List<GroupInvitationDTO> invitations = groupInvitationService.getPendingInvitations(studentCode);
            return ResponseEntity.ok(ResponseUtil.successResponse("Invitaciones pendientes encontradas exitosamente", invitations));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseUtil.errorResponse("Error al buscar las invitaciones pendientes"));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtil.errorResponse("Error interno al buscar las invitaciones pendientes"));
        }
    }

    @GetMapping("/sent/{groupId}")
    public ResponseEntity<?> getSentInvitations(@PathVariable UUID groupId) {
        try {
            List<GroupInvitationDTO> invitations = groupInvitationService.getSentInvitations(groupId);
            return ResponseEntity.ok(ResponseUtil.successResponse("Invitaciones enviadas encontradas exitosamente", invitations));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseUtil.errorResponse(ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtil.errorResponse("Error interno al buscar las invitaciones enviadas"));
        }
    }

}