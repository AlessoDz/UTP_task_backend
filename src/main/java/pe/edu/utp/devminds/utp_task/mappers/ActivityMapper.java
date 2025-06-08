package pe.edu.utp.devminds.utp_task.mappers;

import org.springframework.stereotype.Component;
import pe.edu.utp.devminds.utp_task.dto.ActivityDTO;
import pe.edu.utp.devminds.utp_task.entities.AcademicCycle;
import pe.edu.utp.devminds.utp_task.entities.Activity;
import pe.edu.utp.devminds.utp_task.entities.User;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ActivityMapper {

    public ActivityDTO toDTO(Activity activity) {
        ActivityDTO dto = new ActivityDTO();
        dto.setId(activity.getId());
        dto.setName(activity.getName());
        dto.setStartTime(activity.getStartTime());
        dto.setEndTime(activity.getEndTime());
        dto.setHasAlarm(activity.isHasAlarm());
        dto.setLocation(activity.getLocation());
        dto.setLink(activity.getLink());
        dto.setInstructor(activity.getInstructor());
        dto.setType(activity.getType());
        dto.setRepeatDays(activity.getRepeatDays());
        dto.setRemote(activity.isRemote());
        dto.setAcademicCycleId(activity.getAcademicCycle().getId());
        dto.setParticipantsIds(activity.getParticipants().stream().map(User::getId).collect(Collectors.toSet()));
        dto.setAudit(activity.getAudit());  // Mapeo de auditoría
        return dto;
    }

    public Activity toEntity(ActivityDTO dto) {
        Activity activity = new Activity();
        activity.setId(dto.getId());
        activity.setName(dto.getName());
        activity.setStartTime(dto.getStartTime());
        activity.setEndTime(dto.getEndTime());
        activity.setHasAlarm(dto.isHasAlarm());
        activity.setLocation(dto.getLocation());
        activity.setLink(dto.getLink());
        activity.setInstructor(dto.getInstructor());
        activity.setType(dto.getType());
        activity.setRepeatDays(dto.getRepeatDays());
        activity.setRemote(dto.isRemote());

        // Asignar el ciclo académico y los participantes basados en los IDs
        AcademicCycle academicCycle = new AcademicCycle();
        academicCycle.setId(dto.getAcademicCycleId());
        activity.setAcademicCycle(academicCycle);

        Set<User> participants = dto.getParticipantsIds().stream().map(id -> {
            User user = new User();
            user.setId(id);
            return user;
        }).collect(Collectors.toSet());
        activity.setParticipants(participants);

        return activity;
    }
}

