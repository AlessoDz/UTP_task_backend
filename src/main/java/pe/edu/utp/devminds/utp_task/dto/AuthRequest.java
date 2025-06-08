package pe.edu.utp.devminds.utp_task.dto;

import lombok.Data;

@Data
public class AuthRequest {

    private String code;
    private String password;

}
