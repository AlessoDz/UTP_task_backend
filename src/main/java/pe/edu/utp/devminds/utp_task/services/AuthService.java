package pe.edu.utp.devminds.utp_task.services;

import pe.edu.utp.devminds.utp_task.dto.AuthRequest;
import pe.edu.utp.devminds.utp_task.dto.AuthResponse;
import pe.edu.utp.devminds.utp_task.dto.UserDTO;

public interface AuthService {

    AuthResponse authenticateUser(AuthRequest authRequest);

    UserDTO getCurrentUser();

}
