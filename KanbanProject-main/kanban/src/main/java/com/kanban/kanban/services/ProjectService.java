package com.kanban.kanban.services;

import com.kanban.kanban.domain.Project;
import com.kanban.kanban.domain.Task;
import com.kanban.kanban.repository.IProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProjectService implements IProjectService{
    @Autowired
    IProjectRepository projectRepository;

    @Override
    public Project createProject(Project project) {
        return projectRepository.insert(project);
    }

    @Override
    public Project getProject(String name) {
        return projectRepository.findById(name).get();
    }

    @Override
    public boolean deleteProject(String name) {
        projectRepository.deleteById(name);
        return true;
    }

    @Override
    public boolean saveChanges(String name,Map<String, List<Task>> columns) {
        Project project = projectRepository.findById(name).get();
        project.setColumns(columns);
        projectRepository.save(project);

        return true;
    }
}
