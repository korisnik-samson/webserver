package org.example.webserver.components.milestone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class MilestoneController {
    private final MilestoneService milestoneService;

    @Autowired
    public MilestoneController(MilestoneService milestoneService) {
        this.milestoneService = milestoneService;
    }

    @GetMapping(path = "api/milestones")
    @ResponseBody
    public ResponseEntity<?> getMilestones(@RequestParam(value = "id", required = false) Integer id) {
        if (id != null) {
            Optional<MilestoneModel> task = milestoneService.getMilestone(id);
            return task.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

        } else {
            List<MilestoneModel> allTasks = milestoneService.getMilestones();
            return ResponseEntity.ok(allTasks);
        }
    }
}
