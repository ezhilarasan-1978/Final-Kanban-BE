package com.kanban.kanban.services;

import com.kanban.kanban.domain.User;

public interface IUserService {
    User registerUser(User user);
    User userDetails(String userName);
    boolean addProjectList(String userName,String projectName);
    boolean removeProjectList(String userName,String projectName);
}
