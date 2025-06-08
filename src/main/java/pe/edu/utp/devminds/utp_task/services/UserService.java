package pe.edu.utp.devminds.utp_task.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.edu.utp.devminds.utp_task.dto.UserDTO;
import pe.edu.utp.devminds.utp_task.entities.User;

import java.util.List;

public interface UserService {

    UserDTO register(UserDTO userDTO);

    List<UserDTO> findAll();

    Page<UserDTO> findAll(Pageable pageable);

    UserDTO findByCode(String code);

    void deleteByCode(String code);

}
