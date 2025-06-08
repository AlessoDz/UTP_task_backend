package pe.edu.utp.devminds.utp_task.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.devminds.utp_task.dto.TaskDTO;
import pe.edu.utp.devminds.utp_task.services.TaskService;
import pe.edu.utp.devminds.utp_task.utils.ResponseUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin("*")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/change-status/{taskId}")
    public ResponseEntity<?> changeTaskStatus(@PathVariable UUID taskId, @RequestParam String newStatus) {
        try {
            taskService.changeTaskStatus(taskId, newStatus);
            return ResponseEntity.ok(ResponseUtil.successResponse("Estado de la tarea cambiado exitosamente", null));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseUtil.errorResponse(ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtil.errorResponse("Error al cambiar el estado de la tarea"));
        }
    }

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody TaskDTO taskDTO) {
        try {
            TaskDTO createdTask = taskService.createTask(taskDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ResponseUtil.successResponse("Tarea creada exitosamente", createdTask));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseUtil.errorResponse(ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtil.errorResponse("Error al crear la tarea"));
        }
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<?> updateTask(@PathVariable UUID taskId, @RequestBody TaskDTO taskDTO) {
        try {
            TaskDTO updatedTask = taskService.updateTask(taskId, taskDTO);
            return ResponseEntity.ok(ResponseUtil.successResponse("Tarea actualizada exitosamente", updatedTask));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseUtil.errorResponse(ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtil.errorResponse("Error al actualizar la tarea"));
        }
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable UUID taskId) {
        try {
            taskService.deleteTask(taskId);
            return ResponseEntity.ok(ResponseUtil.successResponse("Tarea eliminada exitosamente", null));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseUtil.errorResponse(ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtil.errorResponse("Error al eliminar la tarea"));
        }
    }

    @GetMapping("/creator/{creatorCode}")
    public ResponseEntity<?> getTasksByCreator(@PathVariable String creatorCode) {
        try {
            List<TaskDTO> tasks = taskService.getTasksByCreator(creatorCode);
            return ResponseEntity.ok(ResponseUtil.successResponse("Tareas encontradas exitosamente", tasks));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseUtil.errorResponse("Error al buscar las tareas"));
        }
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<?> getTasksByGroup(@PathVariable UUID groupId) {
        try {
            List<TaskDTO> tasks = taskService.getTasksByGroup(groupId);
            return ResponseEntity.ok(ResponseUtil.successResponse("Tareas del grupo encontradas exitosamente", tasks));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseUtil.errorResponse("Error al buscar las tareas del grupo"));
        }
    }

    @GetMapping("/date-range")
    public ResponseEntity<?> getTasksByDateRange(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {
        try {
            List<TaskDTO> tasks = taskService.getTasksByDateRange(startDate, endDate);
            return ResponseEntity.ok(ResponseUtil.successResponse("Tareas encontradas en el rango de fechas", tasks));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseUtil.errorResponse(ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtil.errorResponse("Error al buscar las tareas en el rango de fechas"));
        }
    }

    @PostMapping("/{taskId}/mark-complete")
    public ResponseEntity<?> markTaskAsComplete(@PathVariable UUID taskId) {
        try {
            taskService.markTaskAsComplete(taskId);
            return ResponseEntity.ok(ResponseUtil.successResponse("Tarea marcada como completada exitosamente", null));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseUtil.errorResponse(ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtil.errorResponse("Error al marcar la tarea como completada"));
        }
    }

    @PostMapping("/{taskId}/activate-streak")
    public ResponseEntity<?> activateStreak(@PathVariable UUID taskId, @RequestParam String userCode) {
        try {
            taskService.activateStreak(taskId, userCode);
            return ResponseEntity.ok(ResponseUtil.successResponse("Racha activada exitosamente", null));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseUtil.errorResponse(ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtil.errorResponse("Error al activar la racha"));
        }
    }

    @PostMapping("/{taskId}/reminder-wsp")
    public ResponseEntity<?> handleTaskReminder(@PathVariable UUID taskId) {
        try {
            taskService.handleTaskReminders(taskId);
            return ResponseEntity.ok(ResponseUtil.successResponse("Recordatorio enviado exitosamente", null));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseUtil.errorResponse(ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtil.errorResponse("Error al enviar el recordatorio"));
        }
    }

}