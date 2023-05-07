package com.kanban.kanban.controller;

import com.kanban.kanban.domain.Project;
import com.kanban.kanban.domain.Task;
import com.kanban.kanban.services.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/project")
public class ProjectController {
    @Autowired
    IProjectService projectService;

    @PostMapping("/add")
    public ResponseEntity<?> addProject(@RequestBody Project project){
        return new ResponseEntity<>(projectService.createProject(project), HttpStatus.CREATED);
    }
    @GetMapping("/{name}")
    public ResponseEntity<?> getProject(@PathVariable String name){
        return new ResponseEntity<>(projectService.getProject(name), HttpStatus.OK);
    }
    @PutMapping("/save/{name}")
    public ResponseEntity<?> updateProject(@PathVariable String name,@RequestBody Map<String, List<Task>> columns){
        return new ResponseEntity<>(projectService.saveChanges(name,columns),HttpStatus.OK);
    }
    @DeleteMapping("/delete/{name}")
    public ResponseEntity<?> deleteProject(@PathVariable String name){
        return new ResponseEntity<>(projectService.deleteProject(name),HttpStatus.OK);
    }
}
