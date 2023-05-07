package com.example.FinalProject.Services;

import com.example.FinalProject.Domain.Employee;
import org.springframework.stereotype.Service;

import java.util.Map;

public interface ISecurityTokenGenerator {
    Map<String, String> generateToken(Employee employee);
}
