package pe.edu.utp.devminds.utp_task.mappers;

import org.springframework.stereotype.Component;
import pe.edu.utp.devminds.utp_task.dto.MedalDTO;
import pe.edu.utp.devminds.utp_task.entities.AcademicCycle;
import pe.edu.utp.devminds.utp_task.entities.Medal;
import pe.edu.utp.devminds.utp_task.entities.User;

@Component
public class MedalMapper {

    public MedalDTO toDTO(Medal medal) {
        MedalDTO dto = new MedalDTO();
        dto.setId(medal.getId());
        dto.setUserId(medal.getUser().getId());
        dto.setAcademicCycleId(medal.getAcademicCycle().getId());
        dto.setType(medal.getType());
        dto.setPointsRequired(medal.getPointsRequired());
        dto.setAwardedAt(medal.getAwardedAt());
        dto.setAudit(medal.getAudit());
        return dto;
    }

    public Medal toEntity(MedalDTO dto) {
        Medal medal = new Medal();
        medal.setId(dto.getId());
        User user = new User();
        user.setId(dto.getUserId());
        medal.setUser(user);

        AcademicCycle academicCycle = new AcademicCycle();
        academicCycle.setId(dto.getAcademicCycleId());
        medal.setAcademicCycle(academicCycle);

        medal.setType(dto.getType());
        medal.setPointsRequired(dto.getPointsRequired());
        medal.setAwardedAt(dto.getAwardedAt());
        return medal;
    }
}
