package com.example.service.impl;

import com.example.EmployeeNotFoundException;
import com.example.dto.EmployeeDTO;
import com.example.entity.Employee;
import com.example.mapper.EmployeeMapper;
import com.example.repository.EmployeeRepository;
import com.example.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImplementation implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = EmployeeMapper.mapToEmployee(employeeDTO);
        Employee saveEmployee = employeeRepository.save(employee);
        EmployeeDTO savedEmployeeDTO = EmployeeMapper.mapToEmployeeDTO((saveEmployee));
        return savedEmployeeDTO;
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) throws EmployeeNotFoundException {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isEmpty()) {
            throw new EmployeeNotFoundException("Employee with id: " + id + " not found");
        } else {
            return EmployeeMapper.mapToEmployeeDTO(employee.get());
        }
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map(EmployeeMapper::mapToEmployeeDTO).collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) throws EmployeeNotFoundException {

        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isEmpty()) {
            throw new EmployeeNotFoundException("Employee with id: " + id + " not found");
        } else {
            Employee employeeToUpdate = employee.get();
            employeeToUpdate.setName(employeeDTO.getName());
            employeeToUpdate.setDepartment(employeeDTO.getDepartment());
            employeeRepository.save(employeeToUpdate);
            return EmployeeMapper.mapToEmployeeDTO(employeeToUpdate);
        }
    }

    @Override
    public void deleteEmployee(Long id) throws EmployeeNotFoundException {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isEmpty()) {
            throw new EmployeeNotFoundException("Employee with id: " + id + " not found");
        } else {
            employeeRepository.deleteById(id);
        }
    }
}

