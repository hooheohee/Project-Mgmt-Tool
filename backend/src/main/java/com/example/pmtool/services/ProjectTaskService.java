package com.example.pmtool.services;

import com.example.pmtool.domain.Backlog;
import com.example.pmtool.domain.ProjectTask;
import com.example.pmtool.repositories.BacklogRepository;
import com.example.pmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;
    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
        Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());
        projectTask.setBacklog(backlog);
        Integer backlogSequence = backlog.getPTSequence() + 1;
        backlog.setPTSequence(backlogSequence);
        projectTask.setProjectSequence(projectIdentifier.toUpperCase() + "-" + backlogSequence);
        projectTask.setProjectIdentifier(projectIdentifier.toUpperCase());
        if (projectTask.getPriority() == null || projectTask.getPriority() == 0) {
            projectTask.setPriority(3);
        }
        if (projectTask.getStatus() == null || projectTask.getStatus() == "") {
            projectTask.setStatus("TO DO");
        }
        return projectTaskRepository.save(projectTask);
    }

    public Iterable<ProjectTask> findBacklogById(String backlog_id) {
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(backlog_id);
    }
}
