package pe.edu.utp.devminds.utp_task.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.devminds.utp_task.dto.MedalDTO;
import pe.edu.utp.devminds.utp_task.services.MedalService;
import pe.edu.utp.devminds.utp_task.utils.ResponseUtil;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/medals")
@CrossOrigin("*")
public class MedalController {

    @Autowired
    private MedalService medalService;

    @PostMapping
    public ResponseEntity<?> createMedal(@RequestBody MedalDTO medalDTO) {
        try {
            MedalDTO createdMedal = medalService.createMedal(medalDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ResponseUtil.successResponse("Medalla creada exitosamente", createdMedal));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseUtil.errorResponse(ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtil.errorResponse("Error al crear la medalla"));
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getMedalsByUser(@PathVariable UUID userId) {
        try {
            List<MedalDTO> medals = medalService.getMedalsByUser(userId);
            return ResponseEntity.ok(ResponseUtil.successResponse("Medallas encontradas exitosamente", medals));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseUtil.errorResponse("Error al buscar las medallas del usuario"));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtil.errorResponse("Error interno al buscar las medallas del usuario"));
        }
    }

    @GetMapping("/cycle/{academicCycleId}")
    public ResponseEntity<?> getMedalsByAcademicCycle(@PathVariable UUID academicCycleId) {
        try {
            List<MedalDTO> medals = medalService.getMedalsByAcademicCycle(academicCycleId);
            return ResponseEntity.ok(ResponseUtil.successResponse("Medallas encontradas exitosamente", medals));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseUtil.errorResponse("Error al buscar las medallas del ciclo académico"));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtil.errorResponse("Error interno al buscar las medallas del ciclo académico"));
        }
    }

    @GetMapping("/progress/{userId}/{academicCycleId}")
    public ResponseEntity<?> getUserProgress(@PathVariable UUID userId, @PathVariable UUID academicCycleId) {
        try {
            MedalDTO progress = medalService.getUserProgress(userId, academicCycleId);
            return ResponseEntity.ok(ResponseUtil.successResponse("Progreso del usuario encontrado exitosamente", progress));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseUtil.errorResponse("Error al buscar el progreso del usuario"));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtil.errorResponse("Error interno al buscar el progreso del usuario"));
        }
    }

}