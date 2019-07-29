package com.example.pmtool.services;

import com.example.pmtool.domain.Backlog;
import com.example.pmtool.domain.Project;
import com.example.pmtool.domain.User;
import com.example.pmtool.exceptions.ProjectIdException;
import com.example.pmtool.repositories.ProjectRepository;
import com.example.pmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserRepository userRepository;

    public Project saveProject(Project project, String username) {
        try {
            User user = userRepository.findByUsername(username);
            project.setUser(user);
            project.setProjectLeader(user.getUsername());
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            Backlog backlog = new Backlog();
            project.setBacklog(backlog);
            backlog.setProject(project);
            backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        } catch (Exception e) {
            throw new ProjectIdException("Project ID '" + project.getProjectIdentifier().toUpperCase() + "' already exists.");
        }
    }

    public Project updateProject(String projectId, Project project, String username) {
        Project p = findProjectByIdentifier(projectId, username);
        project.setId(p.getId());
        project.setProjectIdentifier(p.getProjectIdentifier());
        if (project.getDescription() == null) project.setDescription(p.getDescription());
        if (project.getProjectName() == null) project.setProjectName(p.getProjectName());
        if (project.getBacklog() == null) project.setBacklog(p.getBacklog());
        if (project.getCreated_at() == null) project.setCreated_at(p.getCreated_at());
        if (project.getProjectLeader() == null) project.setProjectLeader(p.getProjectLeader());
        if (project.getEnd_date() == null) project.setEnd_date(p.getEnd_date());
        if (project.getStart_date() == null) project.setStart_date(p.getStart_date());
        return projectRepository.save(project);
    }

    public Project findProjectByIdentifier(String projectId, String username) {
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if (project == null) throw new ProjectIdException("Project '" + projectId.toUpperCase() + "' does not exist.");
        if (project.getProjectLeader() == null || !project.getProjectLeader().equals(username))
            throw new ProjectIdException("Project '" + projectId.toUpperCase() + "' not found in your account");
        return project;
    }

    public Iterable<Project> findAllProjects(String username) {
        return projectRepository.findAllByProjectLeader(username);
    }

    public void deleteProjectByIdentifier(String projectId, String username) {

        projectRepository.delete(findProjectByIdentifier(projectId, username));
    }
}
