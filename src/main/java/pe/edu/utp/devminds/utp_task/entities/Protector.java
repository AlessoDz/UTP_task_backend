package pe.edu.utp.devminds.utp_task.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "protectors")
public class Protector {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDateTime activatedAt;

    @ManyToOne
    @JoinColumn(name = "streak_id")
    private Streak streak;

}

