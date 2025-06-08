package pe.edu.utp.devminds.utp_task.mappers;

import org.springframework.stereotype.Component;
import pe.edu.utp.devminds.utp_task.dto.TaskDTO;
import pe.edu.utp.devminds.utp_task.entities.Group;
import pe.edu.utp.devminds.utp_task.entities.Subtask;
import pe.edu.utp.devminds.utp_task.entities.Task;
import pe.edu.utp.devminds.utp_task.entities.User;

import java.util.stream.Collectors;

@Component
public class TaskMapper {

    public TaskDTO toDTO(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setPriority(task.getPriority());
        dto.setStatus(task.getStatus());
        dto.setCreatorId(task.getCreator().getId());
        dto.setGroupId(task.getGroup() != null ? task.getGroup().getId() : null);
        dto.setSubtaskIds(task.getSubtasks().stream().map(Subtask::getId).collect(Collectors.toSet()));
        dto.setStartDate(task.getStartDate());
        dto.setEndDate(task.getEndDate());
        dto.setRepeating(task.isRepeating());
        dto.setRepeatFrequency(task.getRepeatFrequency());
        dto.setRepeatDays(task.getRepeatDays());
        dto.setReminderWsp(task.isReminderWsp());
        dto.setRepeatHandlingOption(task.getRepeatHandlingOption());
        dto.setAudit(task.getAudit());
        return dto;
    }

    public Task toEntity(TaskDTO dto) {
        Task task = new Task();
        task.setId(dto.getId());
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setPriority(dto.getPriority());
        task.setStatus(dto.getStatus());

        // Asignar el creador (usando el ID)
        task.setCreator(new User());
        task.getCreator().setId(dto.getCreatorId());

        // Asignar el grupo (usando el ID)
        if (dto.getGroupId() != null) {
            task.setGroup(new Group());
            task.getGroup().setId(dto.getGroupId());
        }

        // Subtareas pueden ser asignadas más tarde
        task.setStartDate(dto.getStartDate());
        task.setEndDate(dto.getEndDate());
        task.setRepeating(dto.isRepeating());
        task.setRepeatFrequency(dto.getRepeatFrequency());
        task.setRepeatDays(dto.getRepeatDays());
        task.setReminderWsp(dto.isReminderWsp());

        task.setRepeatHandlingOption(dto.getRepeatHandlingOption());  // Asignar la opción de manejo de fechas conflictivas
        return task;
    }
}
