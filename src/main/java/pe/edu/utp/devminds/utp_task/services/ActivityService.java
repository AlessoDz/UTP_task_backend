package pe.edu.utp.devminds.utp_task.services;

import pe.edu.utp.devminds.utp_task.dto.ActivityDTO;

import java.util.List;
import java.util.UUID;

public interface ActivityService {

    ActivityDTO createActivity(ActivityDTO activityDTO);

    List<ActivityDTO> getAllActivities();

    ActivityDTO getActivityById(UUID id);

    void deleteActivity(UUID id);

    // Asignar estudiantes a la actividad usando el code
    void assignStudentToActivity(String studentCode, UUID activityId);

}

