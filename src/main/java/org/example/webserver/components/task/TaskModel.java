package org.example.webserver.components.task;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.example.webserver.components.project.ProjectModel;
import org.example.webserver.components.subtask.SubtaskModel;
import org.example.webserver.components.user.UserModel;
import org.example.webserver.lib.types.IsObjectDeleted;
import org.example.webserver.lib.types.TaskPriority;
import org.example.webserver.lib.types.TaskStatus;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "task")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "task", uniqueConstraints = {@UniqueConstraint(columnNames = {"task_id"})})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TaskModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Integer id;

    @Column(name = "task_title")
    private String title;

    @Column(name = "task_description")
    private String description;

    @Column(name = "task_status")
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Column(name = "task_start_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @Column(name = "task_due_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dueDate;

    @Column(name = "task_estimated_hours")
    private Integer estimatedHours;

    @Column(name = "task_actual_hours")
    private Integer actualHours;

    @OneToMany(mappedBy = "task")
    private Set<SubtaskModel> subtasks = new HashSet<>();

    @ManyToMany(mappedBy = "tasks")
    private Set<UserModel> users = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "project_id")
    private ProjectModel project;

    @Column(name = "task_priority")
    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    @Column(name = "is_deleted")
    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private IsObjectDeleted isDeleted;

    // TODO: Create fields for all existing Relationships
}
