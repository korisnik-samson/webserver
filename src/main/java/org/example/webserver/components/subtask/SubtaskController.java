package org.example.webserver.components.subtask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class SubtaskController {
    private final SubtaskService subtaskService;

    @Autowired
    public SubtaskController(SubtaskService subtaskService) {
        this.subtaskService = subtaskService;
    }

    @GetMapping(path = "/api/tasks/{taskId}/subtasks")
    @ResponseBody
    public ResponseEntity<?> getSubtasks(@PathVariable("taskId")Integer taskId, @RequestParam(value = "id", required = false) Integer id) {
        if (id != null) {
            Optional<SubtaskModel> subtasks = subtaskService.getSubtaskById(id);
            return subtasks.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

        } else {
            List<SubtaskModel> allSubtasks = subtaskService.getSubtasks();
            return ResponseEntity.ok(allSubtasks);
        }
    }

    // TODO: Add a way to very that the task exists
    @PostMapping(path = "api/task/{taskId}/subtasks")
    public ResponseEntity<String> createSubtask(@PathVariable("taskId")Integer taskId, @RequestBody SubtaskModel subtaskModel) {
        return ResponseEntity.ok(this.subtaskService.createSubtask(taskId, subtaskModel));
    }

    @PutMapping(path = "api/tasks/{taskId}/subtasks/{subtaskId}")
    public String updateSubtask(@PathVariable("taskId")Integer taskId, @PathVariable("subtaskId")Integer subtaskId, @RequestBody SubtaskModel updatedSubtask) {
        return this.subtaskService.updateSubtask(taskId, subtaskId, updatedSubtask);
    }

    @DeleteMapping(path = "api/tasks/{taskId}/subtasks/{subtaskId}")
    public String deleteSubtask(@PathVariable("taskId")Integer taskId, @PathVariable("subtaskId")Integer subtaskId) {
        return this.subtaskService.deleteSubtask(taskId, subtaskId);
    }
}
