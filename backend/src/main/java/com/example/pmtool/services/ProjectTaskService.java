package com.example.pmtool.services;

import com.example.pmtool.domain.Backlog;
import com.example.pmtool.domain.Project;
import com.example.pmtool.domain.ProjectTask;
import com.example.pmtool.exceptions.NotFoundException;
import com.example.pmtool.exceptions.ProjectIdException;
import com.example.pmtool.repositories.BacklogRepository;
import com.example.pmtool.repositories.ProjectRepository;
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
    @Autowired
    private ProjectRepository projectRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
        try {
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
        } catch (Exception e) {
            throw new ProjectIdException("Project '" + projectIdentifier.toUpperCase() + "' does not exist.");
        }
    }

    public Iterable<ProjectTask> findBacklogById(String backlog_id) {
        Project project = projectRepository.findByProjectIdentifier(backlog_id);
        if (project == null) {
            throw new ProjectIdException("Project '" + backlog_id.toUpperCase() + "' does not exist.");
        }
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(backlog_id);
    }

    public ProjectTask findPTbyProjectSequence(String backlog_id, String sequence) {
        Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
        if (backlog == null) {
            throw new ProjectIdException("Project '" + backlog_id.toUpperCase() + "' does not exist.");
        }
        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(sequence);
        if(projectTask == null) {
            throw new NotFoundException("Project task '" + sequence.toUpperCase() + "' does not exist.");
        }
        if(!projectTask.getProjectIdentifier().equalsIgnoreCase(backlog_id)) {
            throw new NotFoundException("Project task '" + sequence.toUpperCase() + "' does not belong to Project '" + backlog_id.toUpperCase() +"'.");
        }
        return projectTask;
    }

    public ProjectTask updateProjectTaskBySequence(ProjectTask projectTask, String backlog_id, String pt_id) {
        ProjectTask pt = findPTbyProjectSequence(backlog_id, pt_id);
        projectTask.setProjectSequence(pt_id);
        return projectTaskRepository.save(projectTask);
    }

    public void deletePTbyProjectSequence(String backlog_id, String pt_id) {
        ProjectTask pt = findPTbyProjectSequence(backlog_id, pt_id);
        projectTaskRepository.delete(pt);
    }
}
