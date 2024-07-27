package org.example.webserver.components.milestone;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MilestoneRepository extends JpaRepository<MilestoneModel, Integer> {
}
