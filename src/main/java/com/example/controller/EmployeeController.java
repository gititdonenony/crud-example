package com.example.controller;

import com.example.EmployeeNotFoundException;
import com.example.dto.EmployeeDTO;
import com.example.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/employee")
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO createdEmployee = employeeService.createEmployee(employeeDTO);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) throws EmployeeNotFoundException {
        try {
            EmployeeDTO employeeDTO = employeeService.getEmployeeById(id);
            return new ResponseEntity<>(employeeDTO, HttpStatus.OK);
        } catch (EmployeeNotFoundException e) {
            throw e;
        }

    }

    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<EmployeeDTO> employeeDTOs = employeeService.getAllEmployees();
        return new ResponseEntity<>(employeeDTOs, HttpStatus.OK);
    }

}
