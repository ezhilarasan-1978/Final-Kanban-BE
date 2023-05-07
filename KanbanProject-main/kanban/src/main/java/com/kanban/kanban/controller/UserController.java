package com.kanban.kanban.controller;

import com.kanban.kanban.domain.User;
import com.kanban.kanban.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    @Autowired
    IUserService userService;

    // http://localhost:8007/api/v1/user/register
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user){
        return new ResponseEntity<>(userService.registerUser(user), HttpStatus.CREATED);
    }
    @GetMapping("/{username}")
    public ResponseEntity<?> userDetails(@PathVariable String username){
        return new ResponseEntity<>(userService.userDetails(username), HttpStatus.OK);
    }
    @GetMapping("/addProject/{username}/{projectName}")
    public ResponseEntity<?> addProject(@PathVariable String projectName,@PathVariable String username){
        return new ResponseEntity<>(userService.addProjectList(username,projectName), HttpStatus.OK);
    }
    @GetMapping("/removeProject/{username}/{projectName}")
    public ResponseEntity<?> removeProject(@PathVariable String projectName,@PathVariable String username){
        return new ResponseEntity<>(userService.removeProjectList(username,projectName), HttpStatus.OK);
    }
}
