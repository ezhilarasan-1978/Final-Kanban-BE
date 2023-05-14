package com.example.FinalProject.Controller;

import com.example.FinalProject.Domain.Employee;
import com.example.FinalProject.Domain.EmployeeDTO;
import com.example.FinalProject.Exception.EmployeeAlreadyExistException;
import com.example.FinalProject.Exception.EmployeeNotFoundException;
import com.example.FinalProject.Services.IEmployeeServices;
import com.example.FinalProject.Services.ISecurityTokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/v1/auth")
public class EmployeeController {
    @Autowired
    private IEmployeeServices iEmployeeServices;
    @Autowired
    private ISecurityTokenGenerator iSecurityTokenGenerator;

    // http://localhost:3033/api/v1/auth/addUser
    @PostMapping("/addUser")
    public ResponseEntity<?> addNewUser(@RequestBody EmployeeDTO employeeDTO) throws EmployeeAlreadyExistException {
        Employee employee = new Employee(employeeDTO.getUserName(), employeeDTO.getPassword());
        return new ResponseEntity<>(iEmployeeServices.addEmployee(employee), HttpStatus.OK);
    }

    //    http://localhost:3033/api/v1/auth/login
//    @PostMapping("/login")
//    public ResponseEntity<?> loginCustomer(@RequestBody Employee employee) throws EmployeeNotFoundException {
//        Employee fetchedCustomer = iEmployeeServices.getEmployee(employee);
//
//        if(fetchedCustomer!=null){
//            return new ResponseEntity<>(iSecurityTokenGenerator.generateToken(fetchedCustomer), HttpStatus.OK);
//        }else{
//            return new ResponseEntity<>("The Authentication was failed", HttpStatus.EXPECTATION_FAILED);
//        }
//    }
    @PostMapping("/login")
    public ResponseEntity<?> loginCustomer(@RequestBody Employee employee) throws EmployeeNotFoundException {
        try {
            Employee fetchedCustomer = iEmployeeServices.getEmployee(employee);
            if (fetchedCustomer != null) {
                return new ResponseEntity<>(iSecurityTokenGenerator.generateToken(fetchedCustomer), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("The Authentication was failed", HttpStatus.EXPECTATION_FAILED);
            }
        } catch (EmployeeNotFoundException e) {
            return new ResponseEntity<>("The Authentication was failed", HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/findUser/{name}")
    public ResponseEntity<?> getEmployeeByName(@PathVariable String name) {
        return new ResponseEntity<>(iEmployeeServices.getEmployeeByName(name), HttpStatus.OK);
    }

    //    Method for the testing purpose
    public void setISecurityTokenGenerator(ISecurityTokenGenerator securityTokenGenerator) {
        this.iSecurityTokenGenerator = securityTokenGenerator;
    }

    public void setIEmployeeServices(IEmployeeServices employeeServices) {
        this.iEmployeeServices = employeeServices;
    }
}