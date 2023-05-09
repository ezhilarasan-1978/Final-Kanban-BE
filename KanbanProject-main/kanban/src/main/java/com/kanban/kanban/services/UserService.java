package com.kanban.kanban.services;

import com.kanban.kanban.domain.EmployeeDTO;
import com.kanban.kanban.domain.User;
import com.kanban.kanban.proxy.UserProxy;
import com.kanban.kanban.repository.IUserRepository;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService implements IUserService{
    @Autowired
    IUserRepository userRepository;

    UserProxy userProxy;

    @Autowired
    public UserService(UserProxy userProxy) {
        this.userProxy = userProxy;
    }

    @Override
    public User registerUser(User user) {
        EmployeeDTO employeeDTO = new EmployeeDTO(user.getName(),user.getPassword());
        userProxy.addNewUser(employeeDTO);
        return userRepository.insert(user);
    }
    @Override
    public User userDetails(String userName) {
        return userRepository.findById(userName).get();
    }
    @Override
    public boolean addProjectList(String userName,String projectName) {
        User user= userRepository.findById(userName).get();
        List<String> list=user.getProjectList();
        list.add(projectName);
        user.setProjectList(list);
        userRepository.save(user);
        return true;
    }
    @Override
    public boolean removeProjectList(String userName,String projectName) {
        User user= userRepository.findById(userName).get();
        List<String> list=user.getProjectList();
        list.remove(projectName);
        user.setProjectList(list);
        userRepository.save(user);
        return true;
    }

    @Override
    public List<String> getProjectList(String userName) {
        return userRepository.findById(userName).get().getProjectList();
    }
}