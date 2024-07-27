package org.example.webserver.components.user;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.example.webserver.components.project.ProjectModel;
import org.example.webserver.components.subtask.SubtaskModel;
import org.example.webserver.components.task.TaskModel;
import org.example.webserver.lib.Encoder;
import org.example.webserver.lib.types.IsObjectDeleted;
import org.example.webserver.lib.types.UserRole;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "user", uniqueConstraints = { @UniqueConstraint(columnNames = { "user_id" }) })
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_email")
    private String email;

    @Column(name = "user_password")
    @JsonIgnore
    private String password;

    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    // TODO: Use this field to soft delete a user
    @Column(name = "is_deleted")
    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private IsObjectDeleted isDeleted = IsObjectDeleted.FALSE;

    @ManyToMany
    @JoinTable(
        name = "user_task",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "task_id")
    )
    private Set<TaskModel> tasks = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "user_subtask",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "subtask_id")
    )
    private Set<SubtaskModel> subtasks = new HashSet<>();

    @OneToMany(mappedBy = "projectManager")
    private Set<ProjectModel> createdProjects = new HashSet<>();

    // NOTE: The password is hashed before being stored in the database
    @Transient
    public void setPassword(String password) { this.password = Encoder.getMd5(password); }

    public boolean checkPassword(String password) {
        return this.password.equals(Encoder.getMd5(password));
    }
}
