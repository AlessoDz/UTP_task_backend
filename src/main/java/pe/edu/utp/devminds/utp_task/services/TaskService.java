package pe.edu.utp.devminds.utp_task.services;

import pe.edu.utp.devminds.utp_task.dto.TaskDTO;
import pe.edu.utp.devminds.utp_task.entities.Task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface TaskService {

    TaskDTO createTask(TaskDTO taskDTO);

    TaskDTO updateTask(UUID taskId, TaskDTO taskDTO);

    void deleteTask(UUID taskId);

    List<TaskDTO> getTasksByCreator(String creatorCode);

    List<TaskDTO> getTasksByGroup(UUID groupId);

    void markTaskAsComplete(UUID taskId);

    void changeTaskStatus(UUID taskId, String newStatus);

    void activateStreak(UUID taskId, String userCode);

    List<TaskDTO> getTasksByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    void validateAndCreateTask(TaskDTO taskDTO);

    void handleTaskReminders(UUID taskId);

    void handleTaskRepetition(Task task);

}
