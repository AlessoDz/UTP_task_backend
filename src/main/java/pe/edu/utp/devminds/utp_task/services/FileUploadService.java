package pe.edu.utp.devminds.utp_task.services;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {

    String storeImage(MultipartFile file);

    void deleteImage(String fileName);


}
