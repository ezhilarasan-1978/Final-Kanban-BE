package com.kanban.kanban.controller;

import com.kanban.kanban.domain.User;
import com.kanban.kanban.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    IUserService userService;

    // http://localhost:8007/api/v1/user/register
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user){
        return new ResponseEntity<>(userService.registerUser(user), HttpStatus.CREATED);
    }
    @GetMapping("/details")
    public ResponseEntity<?> userDetails(HttpServletRequest httpServletRequest){
        String username= (String) httpServletRequest.getAttribute("attr1");
        System.out.println(username);
        return new ResponseEntity<>(userService.userDetails(username), HttpStatus.OK);
    }
    @GetMapping("/addProject/{projectName}")
    public ResponseEntity<?> addProject(@PathVariable String projectName,HttpServletRequest httpServletRequest){
        String username= (String) httpServletRequest.getAttribute("attr1");
        return new ResponseEntity<>(userService.addProjectList(username,projectName), HttpStatus.OK);
    }
    @GetMapping("/updateProject/{username}/{projectName}")
    public ResponseEntity<?> addProject(@PathVariable String projectName,@PathVariable String username){
        return new ResponseEntity<>(userService.addProjectList(username,projectName), HttpStatus.OK);
    }
    @GetMapping("/removeProject/{projectName}")
    public ResponseEntity<?> removeProject(@PathVariable String projectName,HttpServletRequest httpServletRequest){
        String username= (String) httpServletRequest.getAttribute("attr1");
        return new ResponseEntity<>(userService.removeProjectList(username,projectName), HttpStatus.OK);
    }

    @GetMapping("/projectList/{name}")
    public ResponseEntity<?> getProjectList(@PathVariable String name){
        return new ResponseEntity<>(userService.getProjectList(name),HttpStatus.OK);
    }
}
