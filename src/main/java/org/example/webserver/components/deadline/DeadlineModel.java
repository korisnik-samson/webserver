package org.example.webserver.components.deadline;

import jakarta.persistence.*;
import lombok.*;
import org.example.webserver.components.project.ProjectModel;
import org.example.webserver.lib.types.DeadlineType;

@Entity(name = "deadline")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DeadlineModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deadline_id")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "project_id")
    private ProjectModel project;

    @Column(name = "deadline_type")
    @Enumerated(EnumType.STRING)
    private DeadlineType deadlineType;

    @Column(name = "deadline_date")
    private String deadlineDate;
}

