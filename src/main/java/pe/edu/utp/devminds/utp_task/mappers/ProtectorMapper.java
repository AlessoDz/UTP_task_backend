package pe.edu.utp.devminds.utp_task.mappers;

import org.springframework.stereotype.Component;
import pe.edu.utp.devminds.utp_task.dto.ProtectorDTO;
import pe.edu.utp.devminds.utp_task.entities.Protector;
import pe.edu.utp.devminds.utp_task.entities.Streak;
import pe.edu.utp.devminds.utp_task.entities.User;

@Component
public class ProtectorMapper {

    public ProtectorDTO toDTO(Protector protector) {
        ProtectorDTO dto = new ProtectorDTO();
        dto.setId(protector.getId());
        dto.setUserId(protector.getUser().getId());
        dto.setStreakId(protector.getStreak().getId());
        dto.setActivatedAt(protector.getActivatedAt());
        return dto;
    }

    public Protector toEntity(ProtectorDTO dto) {
        Protector protector = new Protector();
        protector.setId(dto.getId());
        User user = new User();
        user.setId(dto.getUserId());
        protector.setUser(user);
        Streak streak = new Streak();
        streak.setId(dto.getStreakId());
        protector.setStreak(streak);
        protector.setActivatedAt(dto.getActivatedAt());
        return protector;
    }
}
