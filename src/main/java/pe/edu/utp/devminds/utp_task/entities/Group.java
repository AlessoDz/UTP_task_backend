package pe.edu.utp.devminds.utp_task.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "groups_")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    private String description;

    private String photo;  // URL de la imagen del grupo

    @ManyToMany
    @JoinTable(
            name = "group_members",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> members;  // Relación con los estudiantes (usuarios)


    @OneToMany(mappedBy = "group")
    private Set<File> files;

    @OneToMany(mappedBy = "group")
    private Set<Comment> comments;

    @OneToMany(mappedBy = "group")
    private Set<Task> tasks;

    @Embedded
    private Audit audit = new Audit();

}