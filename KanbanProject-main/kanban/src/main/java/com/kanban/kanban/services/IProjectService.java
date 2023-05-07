package com.kanban.kanban.services;

import com.kanban.kanban.domain.Project;
import com.kanban.kanban.domain.Task;

import java.util.List;
import java.util.Map;

public interface IProjectService {
    Project createProject(Project project);
    Project getProject(String name);
    boolean deleteProject(String name);
    boolean saveChanges(String name,Map<String, List<Task>> columns);
}
