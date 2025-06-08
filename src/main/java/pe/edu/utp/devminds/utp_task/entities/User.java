package pe.edu.utp.devminds.utp_task.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 100, nullable = false)
    private String lastname;

    @Column(length = 9, nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String password;

    @Column(length = 9, nullable = false)
    private String phone;

    private String profilePicture;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_role")
    )
    private Set<Role> roles = new HashSet<>();

    @Embedded
    private Audit audit = new Audit();

    @OneToMany(mappedBy = "creator")
    private Set<Task> tasks;

    @OneToMany(mappedBy = "user")
    private Set<Points> points;

    @OneToMany(mappedBy = "user")
    private Set<Streak> streaks;

    @OneToMany(mappedBy = "user")
    private Set<Protector> protectors;

    @ManyToMany(mappedBy = "members")
    private Set<Group> groups;

}