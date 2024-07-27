package org.example.webserver.components.milestone;

import jakarta.persistence.*;
import lombok.*;
import org.example.webserver.components.project.ProjectModel;
import org.example.webserver.lib.types.MilestoneStatus;

@Entity(name = "milestone")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MilestoneModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "milestone_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private ProjectModel project;

    @Column(name = "milestone_name")
    private String milestoneName;

    @Column(name = "due_date")
    private String milestoneDueDate;

    @Column(name = "milestone_status")
    @Enumerated(EnumType.STRING)
    private MilestoneStatus milestoneStatus;
}
