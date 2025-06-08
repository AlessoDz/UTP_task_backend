package pe.edu.utp.devminds.utp_task.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.utp.devminds.utp_task.dto.MedalDTO;
import pe.edu.utp.devminds.utp_task.entities.AcademicCycle;
import pe.edu.utp.devminds.utp_task.entities.Medal;
import pe.edu.utp.devminds.utp_task.entities.User;
import pe.edu.utp.devminds.utp_task.mappers.MedalMapper;
import pe.edu.utp.devminds.utp_task.repositories.AcademicCycleRepository;
import pe.edu.utp.devminds.utp_task.repositories.MedalRepository;
import pe.edu.utp.devminds.utp_task.repositories.UserRepository;
import pe.edu.utp.devminds.utp_task.services.MedalService;
import pe.edu.utp.devminds.utp_task.services.PointsService;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MedalServiceImpl implements MedalService {

    @Autowired
    private MedalRepository medalRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AcademicCycleRepository academicCycleRepository;

    @Autowired
    private MedalMapper medalMapper;

    @Autowired
    private PointsService pointsService;

    // Crear una nueva medalla para un usuario
    @Override
    public MedalDTO createMedal(MedalDTO medalDTO) {
        Medal medal = medalMapper.toEntity(medalDTO);
        medal.setAwardedAt(LocalDateTime.now());  // Fecha de otorgamiento
        medal = medalRepository.save(medal);
        return medalMapper.toDTO(medal);
    }

    // Obtener medallas por usuario
    @Override
    public List<MedalDTO> getMedalsByUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        List<Medal> medals = medalRepository.findByUser(user);
        return medals.stream()
                .map(medalMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Obtener medallas por ciclo académico
    @Override
    public List<MedalDTO> getMedalsByAcademicCycle(UUID academicCycleId) {
        AcademicCycle academicCycle = academicCycleRepository.findById(academicCycleId)
                .orElseThrow(() -> new RuntimeException("Ciclo académico no encontrado"));
        List<Medal> medals = medalRepository.findByAcademicCycle(academicCycle);
        return medals.stream()
                .map(medalMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Verificar si un usuario ha alcanzado los puntos necesarios para recibir una medalla
    @Override
    public void checkAndAssignMedal(UUID userId, UUID academicCycleId, int points) {
        String medalType = getMedalType(points);

        if (medalType != null) {
            Medal medal = new Medal();
            medal.setUser(new User());
            medal.getUser().setId(userId);
            medal.setAcademicCycle(new AcademicCycle());
            medal.getAcademicCycle().setId(academicCycleId);
            medal.setType(medalType);
            medal.setPointsRequired(points);
            createMedal(medalMapper.toDTO(medal));  // Crear la medalla
        }
    }

    // Obtener la medalla correspondiente según los puntos
    private String getMedalType(int points) {
        if (points >= 3000) {
            return "Rubí";
        } else if (points >= 2000) {
            return "Platino";
        } else if (points >= 1250) {
            return "Oro";
        } else if (points >= 800) {
            return "Plata";
        } else if (points >= 500) {
            return "Bronce";
        }
        return null;  // Si no ha alcanzado ninguna medalla
    }

    // Obtener el progreso actual del estudiante (puntos y medalla)
    @Override
    public MedalDTO getUserProgress(UUID userId, UUID academicCycleId) {
        // Obtener los puntos del usuario en el ciclo actual
        int totalPoints = pointsService.getTotalPointsForUserInCycle(userId, academicCycleId);

        // Obtener la medalla actual del usuario
        MedalDTO medalDTO = getMedalsByUser(userId).stream()
                .filter(medal -> medal.getAcademicCycleId().equals(academicCycleId))
                .max(Comparator.comparingInt(MedalDTO::getPointsRequired))
                .orElse(null);

        return new MedalDTO(
                userId,
                academicCycleId,
                medalDTO != null ? medalDTO.getType() : "Ninguna",
                totalPoints,
                medalDTO != null ? medalDTO.getAwardedAt() : null
        );
    }
}
