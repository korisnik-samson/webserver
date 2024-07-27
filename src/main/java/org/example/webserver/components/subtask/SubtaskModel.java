package org.example.webserver.components.subtask;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.example.webserver.components.task.TaskModel;
import org.example.webserver.components.user.UserModel;
import org.example.webserver.lib.types.SubtaskStatus;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "subtask")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "subtask", uniqueConstraints = { @UniqueConstraint(columnNames = {"subtask_id"}) })
public class SubtaskModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subtask_id")
    private Integer id;

    // represents the parent task
    @ManyToOne
    @JoinColumn(name = "task_id")
    private TaskModel task;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "subtasks")
    private Set<UserModel> users = new HashSet<>();

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private SubtaskStatus status;

    public void setStatus(String status) { this.status = SubtaskStatus.valueOf(status); }
}
