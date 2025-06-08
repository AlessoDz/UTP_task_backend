package pe.edu.utp.devminds.utp_task.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pe.edu.utp.devminds.utp_task.dto.UserDTO;
import pe.edu.utp.devminds.utp_task.entities.Role;
import pe.edu.utp.devminds.utp_task.entities.User;
import pe.edu.utp.devminds.utp_task.mappers.UserMapper;
import pe.edu.utp.devminds.utp_task.repositories.RoleRepository;
import pe.edu.utp.devminds.utp_task.repositories.UserRepository;
import pe.edu.utp.devminds.utp_task.services.UserService;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDTO register(UserDTO userDTO) {
        if (userRepository.existsByCode(userDTO.getCode())) {
            throw new RuntimeException("El código ya está registrado");
        }

        // Buscar o crear el rol "Estudiante"
        Role role = roleRepository.findByName("Estudiante")
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName("Estudiante");
                    return roleRepository.save(newRole);
                });

        // Convertir el DTO a entidad y setear el rol
        User user = userMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCode(generateUniqueCode());
        user.getRoles().add(role);

        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDTO)
                .toList();
    }

    @Override
    public Page<UserDTO> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::toDTO);
    }

    @Override
    public UserDTO findByCode(String code) {
        User user = userRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con el código: " + code));
        return userMapper.toDTO(user);
    }
    @Override
    public void deleteByCode(String code) {
        UserDTO user = findByCode(code);
        userRepository.delete(userMapper.toEntity(user));
    }

    // Método para generar un codigo de estudiante
    private String generateUniqueCode() {
        String code;
        do {
            code = "U" + String.format("%08d", new Random().nextInt(100_000_000));
        } while (userRepository.existsByCode(code));
        return code;
    }

}
