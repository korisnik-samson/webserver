package org.example.webserver.components.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<ProjectModel, Integer> {
    @Query(value = "SELECT * FROM project WHERE project_name =: projectName", nativeQuery = true)
    Optional<ProjectModel> findProjectByName(String projectName);

    @Modifying
    @Query(value = "UPDATE project SET project_name = :projectName, project_description = :projectDescription, " +
            "project_start_date = :projectStartDate, project_end_date = :projectDueDate, project_status = :projectStatus" +
            " WHERE project_id = :projectId", nativeQuery = true)
    Integer updateProject(Integer projectId, String projectName, String projectDescription, String projectStartDate, String projectDueDate, String projectStatus);
}
