package org.example.webserver.components.project;

import org.example.webserver.components.task.TaskModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProjectController {
    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping(path = "api/project")
    @ResponseBody
    public ResponseEntity<?> getProjects(@RequestParam(value = "id", required = false) Integer id) {
        if (id != null) {
            Optional<ProjectModel> project = projectService.getProjectById(id);
            return project.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

        } else {
            List<ProjectModel> allProjects = projectService.getProjects();
            return ResponseEntity.ok(allProjects);
        }
    }

    @GetMapping(path = "api/project/{projectName}")
    public Optional<ProjectModel> getProjectByName(@PathVariable("projectName") String projectName) {
        return this.projectService.getProjectByName(projectName);
    }

    @PostMapping(path = "api/project")
    public ProjectModel createProject(@RequestBody ProjectModel projectModel) {
        return this.projectService.createProject(projectModel);
    }

    @PutMapping(path = "api/project/{id}")
    public ResponseEntity<String> updateProject(@PathVariable("id") Integer projectId, @RequestBody ProjectModel projectModel) {
        return ResponseEntity.ok(this.projectService.updateProject(projectId, projectModel));
    }
}
