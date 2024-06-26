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
@RequestMapping("/api/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/create")
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

    @PutMapping("/employeeUpdate/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employeeDTO) throws EmployeeNotFoundException {
        try {
            EmployeeDTO updatedEmployee = employeeService.updateEmployee(id, employeeDTO);
            return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
        } catch (EmployeeNotFoundException e) {
            throw e;
        }
    }

    @DeleteMapping("/employeeDelete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) throws EmployeeNotFoundException {
        try {
            employeeService.deleteEmployee(id);
            return new ResponseEntity<>("Employee deleted successfully", HttpStatus.OK);
        } catch (EmployeeNotFoundException e) {
            throw e;
        }
    }

}
