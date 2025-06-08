package pe.edu.utp.devminds.utp_task.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.devminds.utp_task.dto.AcademicCycleDTO;
import pe.edu.utp.devminds.utp_task.services.AcademicCycleService;
import pe.edu.utp.devminds.utp_task.utils.ResponseUtil;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/academic-cycles")
public class AcademicCycleController {

    @Autowired
    private AcademicCycleService academicCycleService;

    @PostMapping
    public ResponseEntity<?> createAcademicCycle(@RequestBody AcademicCycleDTO academicCycleDTO) {
        try {
            AcademicCycleDTO createdCycle = academicCycleService.createAcademicCycle(academicCycleDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ResponseUtil.successResponse("Ciclo académico creado exitosamente", createdCycle));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseUtil.errorResponse(ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtil.errorResponse("Error al crear el ciclo académico"));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllAcademicCycles() {
        try {
            List<AcademicCycleDTO> cycles = academicCycleService.getAllAcademicCycles();
            return ResponseEntity.ok(ResponseUtil.successResponse("Ciclos académicos encontrados exitosamente", cycles));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseUtil.errorResponse("Error al buscar los ciclos académicos"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAcademicCycleById(@PathVariable UUID id) {
        try {
            AcademicCycleDTO cycle = academicCycleService.getAcademicCycleById(id);
            return ResponseEntity.ok(ResponseUtil.successResponse("Ciclo académico encontrado", cycle));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseUtil.errorResponse(ex.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAcademicCycle(@PathVariable UUID id) {
        try {
            academicCycleService.deleteAcademicCycle(id);
            return ResponseEntity.ok(ResponseUtil.successResponse("Ciclo académico eliminado exitosamente", null));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseUtil.errorResponse(ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtil.errorResponse("Error al eliminar el ciclo académico"));
        }
    }
}