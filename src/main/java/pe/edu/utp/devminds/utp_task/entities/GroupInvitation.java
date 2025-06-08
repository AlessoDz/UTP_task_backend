package pe.edu.utp.devminds.utp_task.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "group_invitations")
public class GroupInvitation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;  // El grupo al que se invita al estudiante

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // El estudiante que recibe la invitación

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InvitationStatus status;  // Estado de la invitación: PENDIENTE, ACEPTADA, RECHAZADA

    @Embedded
    private Audit audit;

    @Column(nullable = false)
    private LocalDateTime expiryDate;  // Fecha de vencimiento de la invitación (7 días después de la creación)
}
