package pe.edu.utp.devminds.utp_task.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.devminds.utp_task.dto.UserDTO;
import pe.edu.utp.devminds.utp_task.entities.User;
import pe.edu.utp.devminds.utp_task.services.UserService;
import pe.edu.utp.devminds.utp_task.utils.ResponseUtil;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody UserDTO userDTO) {
        try {
            UserDTO createdUser = userService.register(userDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ResponseUtil.successResponse("Usuario creado exitosamente", createdUser));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseUtil.errorResponse(ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtil.errorResponse("Error al crear el usuario"));
        }
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            List<UserDTO> users = userService.findAll();
            return ResponseEntity.ok(ResponseUtil.successResponse("Usuarios encontrados exitosamente", users));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseUtil.errorResponse("Error al buscar los usuarios"));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAllPage(@PageableDefault(size = 10, page = 0) Pageable pageable) {
        try {
            Page<UserDTO> users = userService.findAll(pageable);
            return ResponseEntity.ok(ResponseUtil.successResponse("Usuarios encontrados exitosamente", users));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseUtil.errorResponse("Error al buscar los usuarios"));
        }
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<?> findByCode(@PathVariable String code) {
        try {
            UserDTO user = userService.findByCode(code);
            return ResponseEntity.ok(ResponseUtil.successResponse("Usuario encontrado", user));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseUtil.errorResponse(ex.getMessage()));
        }
    }

    @DeleteMapping("/delete/{code}")
    public ResponseEntity<?> deleteByCode(@PathVariable String code) {
        try {
            userService.deleteByCode(code);
            return ResponseEntity.ok(ResponseUtil.successResponse("Usuario eliminado exitosamente", null));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseUtil.errorResponse(ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtil.errorResponse("Error al eliminar el usuario"));
        }
    }

}
