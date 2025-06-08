package pe.edu.utp.devminds.utp_task.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.utp.devminds.utp_task.dto.TaskDTO;
import pe.edu.utp.devminds.utp_task.entities.Group;
import pe.edu.utp.devminds.utp_task.entities.Points;
import pe.edu.utp.devminds.utp_task.entities.Task;
import pe.edu.utp.devminds.utp_task.entities.User;
import pe.edu.utp.devminds.utp_task.mappers.TaskMapper;
import pe.edu.utp.devminds.utp_task.repositories.GroupRepository;
import pe.edu.utp.devminds.utp_task.repositories.TaskRepository;
import pe.edu.utp.devminds.utp_task.repositories.UserRepository;
import pe.edu.utp.devminds.utp_task.services.PointsService;
import pe.edu.utp.devminds.utp_task.services.TaskService;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private TaskMapper taskMapper;

//    @Autowired
//    private StreakService streakService;

    @Autowired
    private PointsService pointsService;

    // Método para cambiar el estado rápidamente de la tarea
    @Override
    public void changeTaskStatus(UUID taskId, String newStatus) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

        task.setStatus(newStatus);
        taskRepository.save(task);

        // Sumar puntos dependiendo del tipo de tarea (personal o grupal)
        sumPoints(task);

        // Activar la racha solo si es necesario
        if ("Completada".equals(newStatus)) {
            activateStreakIfNeeded(task);
        }
    }

    private void sumPoints(Task task) {
        int pointsToAdd = (task.getGroup() == null) ? 10 : 15;  // 10 puntos si es tarea personal, 15 si es grupal

        // Asignar puntos al usuario
        Points points = new Points();
        points.setUser(task.getCreator());
        points.setValue(pointsToAdd);
        points.setAwardedAt(LocalDateTime.now());
        pointsService.addPoints(points);  // Llamamos al servicio que gestiona los puntos
    }

    // Método para activar la racha solo si es necesario
    private void activateStreakIfNeeded(Task task) {
        // Verificamos si el usuario ya tiene tareas completadas hoy
        if (hasCompletedTaskToday(task.getCreator())) {
            // Si ya hay tareas completadas hoy, no activamos la racha, solo sumamos puntos
            return;
        }

        // Activamos la racha solo si no se ha hecho una tarea hoy
        // streakService.activateStreak(task.getCreator());  // Activamos la racha para el usuario
    }

    // Verifica si el usuario ha realizado alguna tarea hoy
    private boolean hasCompletedTaskToday(User user) {
        // LocalDateTime startOfDay = LocalDateTime.now().toLocalDate().atStartOfDay();
        // List<Task> completedTasksToday = taskRepository.findByCreatorAndStatusAndEndDateAfter(user, "Completada", startOfDay);
        //return !completedTasksToday.isEmpty();
        return true;
    }

    @Override
    public TaskDTO createTask(TaskDTO taskDTO) {
        validateAndCreateTask(taskDTO);  // Validar antes de crear la tarea
        Task task = taskMapper.toEntity(taskDTO);
        task = taskRepository.save(task);
        return taskMapper.toDTO(task);
    }

    @Override
    public TaskDTO updateTask(UUID taskId, TaskDTO taskDTO) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

        // Actualización de la tarea
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setPriority(taskDTO.getPriority());
        task.setStatus(taskDTO.getStatus());
        task.setStartDate(taskDTO.getStartDate());
        task.setEndDate(taskDTO.getEndDate());
        task.setRepeating(taskDTO.isRepeating());
        task.setRepeatFrequency(taskDTO.getRepeatFrequency());
        task.setRepeatDays(taskDTO.getRepeatDays());
        task.setReminderWsp(taskDTO.isReminderWsp());
        task.setRepeatHandlingOption(taskDTO.getRepeatHandlingOption());
        task = taskRepository.save(task);
        return taskMapper.toDTO(task);
    }

    @Override
    public void deleteTask(UUID taskId) {
        taskRepository.deleteById(taskId);
    }

    @Override
    public List<TaskDTO> getTasksByCreator(String creatorCode) {
        User creator = userRepository.findByCode(creatorCode)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        List<Task> tasks = taskRepository.findByCreator(creator);
        return tasks.stream()
                .map(taskMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> getTasksByGroup(UUID groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Grupo no encontrado"));
        List<Task> tasks = taskRepository.findByGroup(group);
        return tasks.stream()
                .map(taskMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void markTaskAsComplete(UUID taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
        task.setStatus("Completada");
        taskRepository.save(task);
    }

    @Override
    public void activateStreak(UUID taskId, String userCode) {
        // Activar racha si la tarea es completada
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
        if ("Completada".equals(task.getStatus())) {
            //streakService.activateStreak(task.getCreator());  // Activar racha para el usuario
        }
    }

    @Override
    public List<TaskDTO> getTasksByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        List<Task> tasks = taskRepository.findByStartDateBetween(startDate, endDate);
        return tasks.stream()
                .map(taskMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Validación de la tarea antes de crearla (revisión de repetición)
    @Override
    public void validateAndCreateTask(TaskDTO taskDTO) {
        // Validar si la tarea se repite y el rango de fechas
        if (taskDTO.isRepeating()) {
            handleTaskRepetition(taskMapper.toEntity(taskDTO));
        }
    }

    // Manejo de la repetición de las tareas
    @Override
    public void handleTaskRepetition(Task task) {
        // Lógica para manejar tareas repetitivas basadas en la frecuencia y días
        if ("Diario".equals(task.getRepeatFrequency())) {
            task.setStartDate(LocalDateTime.now()); // Ajustar la fecha de inicio
        } else if ("Semanal".equals(task.getRepeatFrequency())) {
            // Establecer las fechas según los días de la semana
        } else if ("Mensual".equals(task.getRepeatFrequency())) {
            // Ajustar la fecha mensual
            if ("31".equals(task.getRepeatHandlingOption())) {
                task.setStartDate(task.getStartDate().withDayOfMonth(31)); // Asegurarse que la fecha sea 31
            } else if ("LAST_DAY".equals(task.getRepeatHandlingOption())) {
                task.setStartDate(getLastDayOfMonth(task.getStartDate())); // Ajustar al último día del mes
            }
        }
        taskRepository.save(task);
    }

    // Manejo de recordatorios por WhatsApp
    @Override
    public void handleTaskReminders(UUID taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
        if (task.isReminderWsp()) {
            // Lógica para enviar recordatorio a través de WhatsApp
        }
    }

    private LocalDateTime getLastDayOfMonth(LocalDateTime date) {
        return date.with(TemporalAdjusters.lastDayOfMonth());
    }

}

