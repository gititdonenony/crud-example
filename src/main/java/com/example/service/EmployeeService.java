package com.example.service;

import com.example.EmployeeNotFoundException;
import com.example.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeService {
    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO getEmployeeById(Long id) throws EmployeeNotFoundException;

    List<EmployeeDTO> getAllEmployees();

    EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) throws EmployeeNotFoundException;

    void deleteEmployee(Long id) throws EmployeeNotFoundException;
}
