package pe.edu.utp.devminds.utp_task.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "sub_tasks")
public class Subtask {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private boolean completed;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @Embedded
    private Audit audit = new Audit();

}

