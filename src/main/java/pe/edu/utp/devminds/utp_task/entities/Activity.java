package pe.edu.utp.devminds.utp_task.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "activities")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 200)
    private String name;  // Nombre de la actividad (Clase, Examen, Foro, etc.)

    @Column(nullable = false)
    private LocalDateTime startTime;  // Hora de inicio de la actividad

    @Column(nullable = false)
    private LocalDateTime endTime;  // Hora de fin de la actividad

    @Column(nullable = false)
    private boolean hasAlarm;  // Indica si tiene alarma (hora de fin y de inicio opcional)

    @Column(length = 100)
    private String location;  // Ubicación de la actividad (Aula C305, Zoom, etc.)

    @Column(length = 255)
    private String link;  // Link de la actividad (si aplica, por ejemplo, Zoom, UTP+class)

    @Column(nullable = false, length = 100)
    private String instructor;  // Docente encargado de la actividad

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActivityType type;  // Tipo de actividad (Clase, Examen, Foro, etc.)

    @Column(length = 50)
    private String repeatDays;  // Días de la semana para clases, si es recurrente (por ejemplo: "Lunes, Miércoles")

    @Column(nullable = false)
    private boolean isRemote;  // Indica si es remota (Zoom) o presencial

    @ManyToOne
    @JoinColumn(name = "academic_cycle_id", nullable = false)
    private AcademicCycle academicCycle;  // Relación con el ciclo académico

    @ManyToMany
    @JoinTable(
            name = "activity_user",
            joinColumns = @JoinColumn(name = "activity_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> participants;  // Estudiantes participantes en la actividad

    @Embedded
    private Audit audit = new Audit();  // Auditar creación y actualización

}
