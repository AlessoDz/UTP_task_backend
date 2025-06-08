package pe.edu.utp.devminds.utp_task.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.utp.devminds.utp_task.dto.ActivityDTO;
import pe.edu.utp.devminds.utp_task.entities.Activity;
import pe.edu.utp.devminds.utp_task.entities.User;
import pe.edu.utp.devminds.utp_task.mappers.ActivityMapper;
import pe.edu.utp.devminds.utp_task.repositories.ActivityRepository;
import pe.edu.utp.devminds.utp_task.repositories.UserRepository;
import pe.edu.utp.devminds.utp_task.services.ActivityService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActivityMapper activityMapper;

    @Override
    public ActivityDTO createActivity(ActivityDTO activityDTO) {
        Activity activity = activityMapper.toEntity(activityDTO);
        activity = activityRepository.save(activity);
        return activityMapper.toDTO(activity);
    }

    @Override
    public List<ActivityDTO> getAllActivities() {
        return activityRepository.findAll().stream()
                .map(activityMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ActivityDTO getActivityById(UUID id) {
        return activityRepository.findById(id)
                .map(activityMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Actividad no encontrada"));
    }

    @Override
    public void deleteActivity(UUID id) {
        activityRepository.deleteById(id);
    }

    @Override
    public void assignStudentToActivity(String studentCode, UUID activityId) {

        // Obtener al estudiante por su código
        User student = userRepository.findByCode(studentCode)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        // Obtener la actividad por su ID
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new RuntimeException("Actividad no encontrada"));

        // Asignar el estudiante a la actividad
        activity.getParticipants().add(student);
        activityRepository.save(activity);
    }

}

