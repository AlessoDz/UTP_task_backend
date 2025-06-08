package pe.edu.utp.devminds.utp_task.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import pe.edu.utp.devminds.utp_task.entities.Audit;

import java.util.Set;
import java.util.UUID;

@Data
public class UserDTO {

    private UUID id;
    private String name;
    private String lastname;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String code;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String phone;
    private String profilePicture;
    private Set<String> roles;
    private Audit audit;

}
