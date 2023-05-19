package com.kanban.kanban.services;

import com.kanban.kanban.config.NotificationDTO;
import com.kanban.kanban.domain.EmployeeDTO;
import com.kanban.kanban.domain.User;
import com.kanban.kanban.exception.ProjectNotFoundException;
import com.kanban.kanban.exception.UserAlreadyExistException;
import com.kanban.kanban.exception.UserNotFoundException;
import com.kanban.kanban.proxy.ProjectProxy;
import com.kanban.kanban.proxy.UserProxy;
import com.kanban.kanban.repository.IUserRepository;
import org.json.simple.JSONObject;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    private IUserRepository userRepository;

    private UserProxy userProxy;
    private RabbitTemplate rabbitTemplate;
    private DirectExchange directExchange;

    private ProjectProxy projectProxy;

    @Autowired
    public UserService(IUserRepository userRepository, UserProxy userProxy, RabbitTemplate rabbitTemplate, DirectExchange directExchange, ProjectProxy projectProxy) {
        this.userRepository = userRepository;
        this.userProxy = userProxy;
        this.rabbitTemplate = rabbitTemplate;
        this.directExchange = directExchange;
        this.projectProxy = projectProxy;
    }

    @Override
    public User registerUser(User user) throws UserAlreadyExistException {
        if (userRepository.findById(user.getName()).isEmpty()) {
            EmployeeDTO employeeDTO = new EmployeeDTO(user.getName(), user.getPassword());
            userProxy.addNewUser(employeeDTO);
            userRepository.insert(user);
            String message = "Welcome "+user.getName();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Notification",message);
            jsonObject.put("username",user.getName());
            NotificationDTO notificationDTO = new NotificationDTO(jsonObject);
            rabbitTemplate.convertAndSend(directExchange.getName(),"user-routing",notificationDTO);
            return userRepository.insert(user);
        } else {
            throw new UserAlreadyExistException();
        }
    }

    @Override
    public User userDetails(String userName) throws UserNotFoundException {
        if (userRepository.findById(userName).isEmpty()) {
            throw new UserNotFoundException();
        } else {
            return userRepository.findById(userName).get();
        }
    }

    @Override
    public boolean addProjectList(String userName, String projectName) throws UserNotFoundException, ProjectNotFoundException {
        if (userRepository.findById(userName).isEmpty()) {
            throw new UserNotFoundException();
        } else {
            User user = userRepository.findById(userName).get();
            List<String> list = user.getProjectList();
            list.add(projectName);
            user.setProjectList(list);
            String message = "Created "+projectName;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Notification",message);
            jsonObject.put("username",userName);
            NotificationDTO notificationDTO = new NotificationDTO(jsonObject);
            rabbitTemplate.convertAndSend(directExchange.getName(),"user-routing",notificationDTO);
            userRepository.save(user);
            return true;
        }
    }

    @Override
    public boolean removeProjectList(String userName, String projectName) throws UserNotFoundException, ProjectNotFoundException {
        if (userRepository.findById(userName).isEmpty()) {
            throw new UserNotFoundException();
        }
        User user_ = userRepository.findById(userName).get();
        List<String> projectList = user_.getProjectList();
        if (!projectList.contains(projectName)) {
            throw new ProjectNotFoundException();
        } else {
            projectProxy.deleteMemberOfProject(projectName,userName);
            List<String> list = user_.getProjectList();
            list.remove(projectName);
            user_.setProjectList(list);

            userRepository.save(user_);
            return true;
        }
    }

    @Override
    public List<String> getProjectList(String userName) throws UserNotFoundException {
        if (userRepository.findById(userName).isEmpty()) {
            throw new UserNotFoundException();
        } else {
            return userRepository.findById(userName).get().getProjectList();
        }
    }
}