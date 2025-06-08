package pe.edu.utp.devminds.utp_task.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "medals")
public class Medal {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "academic_cycle_id", nullable = false)
    private AcademicCycle academicCycle;  // Ciclo académico al que pertenece la medalla

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private int pointsRequired;

    private LocalDateTime awardedAt;  // Fecha en la que se otorgó la medalla

    @Embedded
    private Audit audit = new Audit();

}

