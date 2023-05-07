package com.example.FinalProject.Repository;

import com.example.FinalProject.Domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    Employee findByUserNameAndPassword(String userName, String password);
}
