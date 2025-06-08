package pe.edu.utp.devminds.utp_task.servicesImpl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pe.edu.utp.devminds.utp_task.services.FileUploadService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public String storeImage(MultipartFile file) {
        try {
            // Validar que el archivo no esté vacío
            if (file.isEmpty()) {
                throw new IllegalArgumentException("El archivo no puede estar vacío");
            }

            // Crear la carpeta 'uploads' si no existe
            Path path = Paths.get(uploadDir);
            if (!Files.exists(path)) {
                Files.createDirectories(path);  // Crea la carpeta si no existe
            }

            // Generar un nombre único para el archivo
            String originalFileName = file.getOriginalFilename();
            String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String uniqueFileName = UUID.randomUUID().toString() + extension;

            // Guardar el archivo en el directorio uploads/
            Path destination = Paths.get(uploadDir, uniqueFileName);
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

            return "/uploads/" + uniqueFileName;  // Ruta relativa para la foto
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar la imagen", e);
        }
    }

    @Override
    public void deleteImage(String fileName) {
        try {
            Path filePath = Paths.get(uploadDir + fileName);
            if (Files.exists(filePath)) {
                Files.delete(filePath);  // Eliminar la imagen
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al eliminar la imagen", e);
        }
    }

}
