package org.example.webserver.components.milestone;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MilestoneService {
    private final MilestoneRepository milestoneRepository;

    public MilestoneService(MilestoneRepository milestoneRepository) {
        this.milestoneRepository = milestoneRepository;
    }

    public List<MilestoneModel> getMilestones() {
        return this.milestoneRepository.findAll();
    }

    public Optional<MilestoneModel> getMilestone(Integer id) {
        return this.milestoneRepository.findById(id);
    }

    public MilestoneModel createMilestone(MilestoneModel milestone) {
        return this.milestoneRepository.save(milestone);
    }

    public MilestoneModel updateMilestone(MilestoneModel milestone) {
        return this.milestoneRepository.save(milestone);
    }

    public void deleteMilestone(Integer id) {
        this.milestoneRepository.deleteById(id);
    }
}
