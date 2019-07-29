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
    private ProjectTaskRepository projectTaskRepository;
    @Autowired
    private ProjectService projectService;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username) {
        Backlog backlog = projectService.findProjectByIdentifier(projectIdentifier, username).getBacklog();
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

    public Iterable<ProjectTask> findBacklogById(String backlog_id, String username) {
        projectService.findProjectByIdentifier(backlog_id, username);
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(backlog_id);
    }

    public ProjectTask findPTbyProjectSequence(String backlog_id, String pt_id, String username) {
        projectService.findProjectByIdentifier(backlog_id, username);
        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);
        if (projectTask == null) {
            throw new NotFoundException("Project task '" + pt_id.toUpperCase() + "' does not exist.");
        }
        if (!projectTask.getProjectIdentifier().equalsIgnoreCase(backlog_id)) {
            throw new NotFoundException("Project task '" + pt_id.toUpperCase() + "' does not belong to Project '" + backlog_id.toUpperCase() + "'.");
        }
        return projectTask;
    }

    public ProjectTask updateProjectTaskBySequence(ProjectTask projectTask, String backlog_id, String pt_id, String username) {
        ProjectTask pt = findPTbyProjectSequence(backlog_id, pt_id, username);
        projectTask.setProjectSequence(pt_id.toUpperCase());
        projectTask.setId(pt.getId());
        if(projectTask.getStatus()==null) projectTask.setStatus(pt.getStatus());
        if(projectTask.getPriority()==null) projectTask.setPriority(pt.getPriority());
        if(projectTask.getDueDate()==null) projectTask.setDueDate(pt.getDueDate());
        if(projectTask.getProjectIdentifier()==null) projectTask.setProjectIdentifier(backlog_id.toUpperCase());
        return projectTaskRepository.save(projectTask);
    }

    public void deletePTbyProjectSequence(String backlog_id, String pt_id, String username) {
        ProjectTask pt = findPTbyProjectSequence(backlog_id, pt_id, username);
        projectTaskRepository.delete(pt);
    }
}
