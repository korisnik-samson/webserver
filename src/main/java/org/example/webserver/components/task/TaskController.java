package org.example.webserver.components.task;

import org.example.webserver.components.user.UserModel;
import org.example.webserver.lib.types.IsObjectDeleted;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TaskController {
    public final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping(path = "api/task")
    @ResponseBody
    public ResponseEntity<?> getTasks(@RequestParam(value = "id", required = false) Integer id) {
        if (id != null) {
            Optional<TaskModel> task = taskService.getTaskById(id);
            return task.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

        } else {
            List<TaskModel> allTasks = taskService.getTasks();
            return ResponseEntity.ok(allTasks);
        }
    }

    @GetMapping("api/task/user/{id}")
    public List<TaskModel> getTasksByUser(@PathVariable("id") Integer userId) {
        return this.taskService.getTasksByUser(userId);
    }

    @PostMapping("api/task")
    public TaskModel createTask(@RequestBody TaskModel task) {
        return this.taskService.createTask(task);
    }

    @PutMapping("api/task/{id}")
    public ResponseEntity<String> updateTask(@PathVariable("id") Integer id, @RequestBody TaskModel task) {
        String message = taskService.updateTask(id, task);
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("api/task/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable("id") Integer id) {
        String message = taskService.deleteTask(id);
        return ResponseEntity.ok(message);
    }

    @PutMapping("api/task/del/{id}/is_deleted={is_deleted}")
    public ResponseEntity<String> softDeleteTask(@PathVariable("id") Integer id, @PathVariable("is_deleted") String isDeleted) {
        String message = taskService.softDeleteTask(id, IsObjectDeleted.valueOf(isDeleted));
        return ResponseEntity.ok(message);
    }
}
