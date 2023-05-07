package com.example.FinalProject.Services;

import com.example.FinalProject.Domain.Employee;
import com.example.FinalProject.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class IEmployeeServicesImp implements IEmployeeServices{

    @Autowired
   private EmployeeRepository employeeRepository;


    @Override
    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployee(Employee employee) {
        return employeeRepository.findByUserNameAndPassword(employee.getUserName(), employee.getPassword());
    }
}
