package pe.edu.utp.devminds.utp_task.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.devminds.utp_task.dto.ActivityDTO;
import pe.edu.utp.devminds.utp_task.services.ActivityService;
import pe.edu.utp.devminds.utp_task.utils.ResponseUtil;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/activities")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @PostMapping
    public ResponseEntity<?> createActivity(@RequestBody ActivityDTO activityDTO) {
        try {
            ActivityDTO createdActivity = activityService.createActivity(activityDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ResponseUtil.successResponse("Actividad creada exitosamente", createdActivity));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseUtil.errorResponse(ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtil.errorResponse("Error al crear la actividad"));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllActivities() {
        try {
            List<ActivityDTO> activities = activityService.getAllActivities();
            return ResponseEntity.ok(ResponseUtil.successResponse("Actividades encontradas exitosamente", activities));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseUtil.errorResponse("Error al buscar las actividades"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getActivityById(@PathVariable UUID id) {
        try {
            ActivityDTO activity = activityService.getActivityById(id);
            return ResponseEntity.ok(ResponseUtil.successResponse("Actividad encontrada exitosamente", activity));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseUtil.errorResponse(ex.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteActivity(@PathVariable UUID id) {
        try {
            activityService.deleteActivity(id);
            return ResponseEntity.ok(ResponseUtil.successResponse("Actividad eliminada exitosamente", null));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseUtil.errorResponse(ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtil.errorResponse("Error al eliminar actividad"));
        }
    }

    @PostMapping("/{activityId}/assign-student/{studentCode}")
    public ResponseEntity<?> assignStudentToActivity(
            @PathVariable String studentCode,
            @PathVariable UUID activityId) {
        try {
            activityService.assignStudentToActivity(studentCode, activityId);
            return ResponseEntity.ok(ResponseUtil.successResponse("Estudiante asignado correctamente", null));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseUtil.errorResponse(ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtil.errorResponse("Error al asignar estudiante a la actividad"));
        }
    }

}