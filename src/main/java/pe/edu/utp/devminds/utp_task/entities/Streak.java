package pe.edu.utp.devminds.utp_task.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "streaks")
public class Streak {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private int currentStreak;

    private int maxStreak;

    @Column(nullable = false)
    private LocalDate lastCompletedAt;

    @OneToMany(mappedBy = "streak")
    private Set<Protector> protectors;

}
