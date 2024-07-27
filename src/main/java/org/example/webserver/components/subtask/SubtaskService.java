package org.example.webserver.components.subtask;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubtaskService {
    private final SubtaskRepository subtaskRepository;

    public SubtaskService(SubtaskRepository subtaskRepository) {
        this.subtaskRepository = subtaskRepository;
    }

    public String createSubtask(Integer taskId, SubtaskModel subtaskModel) {
        int createdRow = this.subtaskRepository.createSubtask(taskId, subtaskModel.getTitle(),
                subtaskModel.getDescription(), String.valueOf(subtaskModel.getStatus()));

        return createdRow == 1 ? "SUBTASK CREATED SUCCESSFULLY" : "SUBTASK CREATION FAILED";
    }

    public List<SubtaskModel> getSubtasks() {
        return this.subtaskRepository.findAll();
    }

    public Optional<SubtaskModel> getSubtaskById(Integer subtaskId) {
        return this.subtaskRepository.findById(subtaskId);
    }

    @Transactional
    public String updateSubtask(Integer taskId, Integer subtaskId, SubtaskModel updatedSubtask) {
        Optional<SubtaskModel> currentTask = this.subtaskRepository.findById(subtaskId);
        SubtaskModel finalSubTask = new SubtaskModel();

        finalSubTask.setId(subtaskId);

        if (updatedSubtask.getTitle() != null)
            finalSubTask.setTitle(updatedSubtask.getTitle());
        else finalSubTask.setTitle(currentTask.get().getTitle());

        if (updatedSubtask.getDescription() != null)
            finalSubTask.setDescription(updatedSubtask.getDescription());
        else finalSubTask.setDescription(currentTask.get().getDescription());

        if (updatedSubtask.getStatus() != null)
            finalSubTask.setStatus(String.valueOf(updatedSubtask.getStatus()));
        else finalSubTask.setStatus(String.valueOf(currentTask.get().getStatus()));

        int updatedSubtaskRow = subtaskRepository.updateSubtask(finalSubTask.getTask().getId(), finalSubTask.getId(), finalSubTask.getTitle(), finalSubTask.getDescription(),
                String.valueOf(finalSubTask.getStatus()));

        return updatedSubtaskRow == 1 ? "SUBTASK UPDATED SUCCESSFULLY" : "SUBTASK UPDATE FAILED";
    }

    public String deleteSubtask(Integer taskId, Integer subtaskId) {
        int deletedRow = this.subtaskRepository.deleteSubtask(taskId, subtaskId);
        return deletedRow == 1 ? "SUBTASK DELETED SUCCESSFULLY" : "SUBTASK DELETION FAILED";
    }
}
