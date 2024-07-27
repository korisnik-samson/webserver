package org.example.webserver.components.dependency;

import jakarta.persistence.*;
import lombok.*;
import org.example.webserver.components.task.TaskModel;
import org.example.webserver.lib.types.DependencyType;

@Entity(name = "dependency")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DependencyModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dependency_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private TaskModel task;

    // TODO: Implement relationship field for dependent_task_id

    @Column(name = "dependency_type")
    @Enumerated(EnumType.STRING)
    private DependencyType dependencyType;
}
