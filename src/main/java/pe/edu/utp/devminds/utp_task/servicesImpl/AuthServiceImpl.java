package pe.edu.utp.devminds.utp_task.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pe.edu.utp.devminds.utp_task.dto.AuthRequest;
import pe.edu.utp.devminds.utp_task.dto.AuthResponse;
import pe.edu.utp.devminds.utp_task.dto.UserDTO;
import pe.edu.utp.devminds.utp_task.dto.UserDetailsImpl;
import pe.edu.utp.devminds.utp_task.mappers.UserMapper;
import pe.edu.utp.devminds.utp_task.repositories.UserRepository;
import pe.edu.utp.devminds.utp_task.services.AuthService;
import pe.edu.utp.devminds.utp_task.utils.JwtUtils;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public AuthResponse authenticateUser(AuthRequest authRequest) {

        // Verificar si estamos recibiendo correctamente los datos
        // System.out.println("Autenticando usuario con código: " + authRequest.getCode());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getCode(), authRequest.getPassword())
        );

        // Mostrar detalles de la autenticación
        // System.out.println("Usuario autenticado: " + authentication.getName());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Verificar si el token está siendo generado correctamente
        String jwt = jwtUtils.generateJwtToken(authentication);
        // System.out.println("JWT generado: " + jwt);

        return new AuthResponse(jwt);  // Devolver el token JWT
    }


    @Override
    public UserDTO getCurrentUser() {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userMapper.toDTO(userRepository.findByCode(userPrincipal.getUsername()).orElseThrow(() -> new RuntimeException("User not found")));

    }

}
