package pe.edu.utp.devminds.utp_task.mappers;

import org.springframework.stereotype.Component;
import pe.edu.utp.devminds.utp_task.dto.StreakDTO;
import pe.edu.utp.devminds.utp_task.entities.Streak;
import pe.edu.utp.devminds.utp_task.entities.User;

import java.util.stream.Collectors;

@Component
public class StreakMapper {

    public StreakDTO toDTO(Streak streak) {
        StreakDTO dto = new StreakDTO();
        dto.setId(streak.getId());
        dto.setUserId(streak.getUser().getId());
        dto.setCurrentStreak(streak.getCurrentStreak());
        dto.setMaxStreak(streak.getMaxStreak());
        dto.setLastCompletedAt(streak.getLastCompletedAt());
        dto.setProtectorIds(streak.getProtectors().stream()
                .map(protector -> protector.getId())
                .collect(Collectors.toSet()));
        return dto;
    }

    public Streak toEntity(StreakDTO dto) {
        Streak streak = new Streak();
        streak.setId(dto.getId());
        User user = new User();
        user.setId(dto.getUserId());
        streak.setUser(user);
        streak.setCurrentStreak(dto.getCurrentStreak());
        streak.setMaxStreak(dto.getMaxStreak());
        streak.setLastCompletedAt(dto.getLastCompletedAt());
        return streak;
    }

}

