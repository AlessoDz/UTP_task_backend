package pe.edu.utp.devminds.utp_task.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.devminds.utp_task.dto.PointsDTO;
import pe.edu.utp.devminds.utp_task.services.PointsService;
import pe.edu.utp.devminds.utp_task.utils.ResponseUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/points")
@CrossOrigin("*")
public class PointsController {

    @Autowired
    private PointsService pointsService;

    @PostMapping
    public ResponseEntity<?> createPoints(@RequestBody PointsDTO pointsDTO) {
        try {
            PointsDTO createdPoints = pointsService.createPoints(pointsDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ResponseUtil.successResponse("Puntos creados exitosamente", createdPoints));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseUtil.errorResponse(ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtil.errorResponse("Error al crear los puntos"));
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getPointsByUser(@PathVariable UUID userId) {
        try {
            List<PointsDTO> points = pointsService.getPointsByUser(userId);
            return ResponseEntity.ok(ResponseUtil.successResponse("Puntos encontrados exitosamente", points));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseUtil.errorResponse("Error al buscar los puntos del usuario"));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtil.errorResponse("Error interno al buscar los puntos del usuario"));
        }
    }

    @GetMapping("/date-range")
    public ResponseEntity<?> getPointsByDateRange(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {
        try {
            List<PointsDTO> points = pointsService.getPointsByDateRange(startDate, endDate);
            return ResponseEntity.ok(ResponseUtil.successResponse("Puntos encontrados en el rango de fechas", points));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseUtil.errorResponse(ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtil.errorResponse("Error interno al buscar los puntos en el rango de fechas"));
        }
    }

    @GetMapping("/total/{userId}/{academicCycleId}")
    public ResponseEntity<?> getTotalPointsForUserInCycle(@PathVariable UUID userId, @PathVariable UUID academicCycleId) {
        try {
            int totalPoints = pointsService.getTotalPointsForUserInCycle(userId, academicCycleId);
            return ResponseEntity.ok(ResponseUtil.successResponse("Puntos totales del usuario en el ciclo académico", totalPoints));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseUtil.errorResponse("Error al buscar los puntos totales del usuario en el ciclo académico"));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtil.errorResponse("Error interno al buscar los puntos totales del usuario en el ciclo académico"));
        }
    }
}