package com.example.FinalProject.Services;

import com.example.FinalProject.Domain.Employee;
import com.example.FinalProject.Exception.EmployeeAlreadyExistException;
import com.example.FinalProject.Exception.EmployeeNotFoundException;

public interface IEmployeeServices {
    Employee addEmployee(Employee employee) throws EmployeeAlreadyExistException;
    Employee getEmployee(Employee employee) throws EmployeeNotFoundException;
    boolean getEmployeeByName(String name);

}