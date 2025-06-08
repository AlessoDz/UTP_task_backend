package pe.edu.utp.devminds.utp_task.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.utp.devminds.utp_task.dto.AcademicCycleDTO;
import pe.edu.utp.devminds.utp_task.entities.AcademicCycle;
import pe.edu.utp.devminds.utp_task.mappers.AcademicCycleMapper;
import pe.edu.utp.devminds.utp_task.repositories.AcademicCycleRepository;
import pe.edu.utp.devminds.utp_task.services.AcademicCycleService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AcademicCycleServiceImpl implements AcademicCycleService {

    @Autowired
    private AcademicCycleRepository academicCycleRepository;

    @Autowired
    private AcademicCycleMapper academicCycleMapper;

    @Override
    public AcademicCycleDTO createAcademicCycle(AcademicCycleDTO academicCycleDTO) {

        // Validación: Verificar si el ciclo académico ya existe por nombre
        if (doesAcademicCycleExist(academicCycleDTO.getName())) {
            throw new RuntimeException("Ciclo académico ya existe con el nombre: " + academicCycleDTO.getName());
        }

        AcademicCycle academicCycle = academicCycleMapper.toEntity(academicCycleDTO);
        academicCycle = academicCycleRepository.save(academicCycle);
        return academicCycleMapper.toDTO(academicCycle);

    }

    @Override
    public List<AcademicCycleDTO> getAllAcademicCycles() {
        return academicCycleRepository.findAll().stream()
                .map(academicCycleMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AcademicCycleDTO getAcademicCycleById(UUID id) {
        return academicCycleRepository.findById(id)
                .map(academicCycleMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Ciclo académico no encontrado"));
    }

    @Override
    public void deleteAcademicCycle(UUID id) {
        academicCycleRepository.deleteById(id);
    }

    // METODOS DE VALIDACIÓN

    // Verifica si el ciclo académico ya existe por nombre
    private boolean doesAcademicCycleExist(String name) {
        return academicCycleRepository.findByName(name) != null;
    }

}
