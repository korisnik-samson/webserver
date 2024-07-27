package org.example.webserver.components.project;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.example.webserver.components.deadline.DeadlineModel;
import org.example.webserver.components.milestone.MilestoneModel;
import org.example.webserver.components.task.TaskModel;
import org.example.webserver.components.user.UserModel;
import org.example.webserver.lib.types.IsObjectDeleted;

import org.example.webserver.lib.types.ProjectStatus;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "project")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "project", uniqueConstraints = { @UniqueConstraint(columnNames = { "project_id" }) })
public class ProjectModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Integer id;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "project_description")
    private String projectDescription;

    @Column(name = "project_start_date")
    private String projectStartDate;

    @Column(name = "project_end_date")
    private String projectEndDate;

    @Column(name = "project_status")
    @Enumerated(EnumType.STRING)
    private ProjectStatus projectStatus;

    @Column(name = "is_deleted")
    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private IsObjectDeleted isDeleted;

    @OneToMany(mappedBy = "project")
    private Set<MilestoneModel> projectMilestones = new HashSet<>();

    @OneToMany(mappedBy = "project")
    private Set<TaskModel> projectTasks = new HashSet<>();

    @OneToOne(mappedBy = "project")
    private DeadlineModel projectDeadline;

    @ManyToOne
    @JoinColumn(name = "project_manager_id")
    private UserModel projectManager;
}
