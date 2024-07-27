package org.example.webserver.components.subtask;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SubtaskRepository extends JpaRepository<SubtaskModel, Integer> {
    @Modifying
    @Query(value = "UPDATE subtask SET title = :title, description = :description, status = :status " +
            "WHERE subtask_id = :id AND task_id = :taskId", nativeQuery = true)
    Integer updateSubtask(Integer taskId, Integer id, String title, String description, String status);

    @Modifying
    @Query(value = "INSERT INTO subtask (task_id, title, description, status) " +
            "VALUES (:taskId, :title, :description, :status)", nativeQuery = true)
    Integer createSubtask(Integer taskId, String title, String description, String status);

    @Modifying
    @Query(value = "DELETE FROM subtask WHERE task_id = :taskId AND subtask_id = :subtaskId", nativeQuery = true)
    Integer deleteSubtask(Integer taskId, Integer subtaskId);
}
