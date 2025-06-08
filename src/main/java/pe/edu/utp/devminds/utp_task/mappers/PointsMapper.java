package pe.edu.utp.devminds.utp_task.mappers;

import org.springframework.stereotype.Component;
import pe.edu.utp.devminds.utp_task.dto.PointsDTO;
import pe.edu.utp.devminds.utp_task.entities.Points;
import pe.edu.utp.devminds.utp_task.entities.User;

@Component
public class PointsMapper {

    public PointsDTO toDTO(Points points) {
        PointsDTO dto = new PointsDTO();
        dto.setId(points.getId());
        dto.setUserId(points.getUser().getId());
        dto.setValue(points.getValue());
        dto.setAwardedAt(points.getAwardedAt());
        dto.setAudit(points.getAudit());
        return dto;
    }

    public Points toEntity(PointsDTO dto) {
        Points points = new Points();
        points.setId(dto.getId());
        User user = new User();
        user.setId(dto.getUserId());
        points.setUser(user);
        points.setValue(dto.getValue());
        points.setAwardedAt(dto.getAwardedAt());
        return points;
    }
}

