package pe.edu.utp.devminds.utp_task.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private String priority; // Alta, Media, Baja

    @Column(nullable = false)
    private String status;   // Por hacer, Pendiente, En revisión, Completada

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @OneToMany(mappedBy = "task")
    private Set<Subtask> subtasks;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Column(nullable = false)
    private boolean isRepeating;

    private String repeatFrequency;  // Diario, Semanal, Mensual
    private String repeatDays;  // Días para repetición semanal

    @Column(nullable = false)
    private boolean reminderWsp;

    @OneToMany(mappedBy = "task")
    private Set<File> files;

    @OneToMany(mappedBy = "task")
    private Set<Comment> comments;

    @Embedded
    private Audit audit = new Audit();

    private String repeatHandlingOption;  // Campo para manejar fechas conflictivas (31, 30, 29)

}

