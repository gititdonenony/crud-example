package com.example.controller;

import com.example.EmployeeNotFoundException;
import com.example.dto.EmployeeDTO;
import com.example.service.EmployeeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
class EmployeeControllerTest {
    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;
    private EmployeeDTO employeeDTO;
    AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    @DisplayName("JUnit test case for createEmployee")
    void testCreateEmployee() {
        // Arrange
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(1L);
        employeeDTO.setName("test");
        employeeDTO.setDepartment("dept");

        EmployeeDTO createdEmployee = new EmployeeDTO();
        createdEmployee.setId(1L);
        createdEmployee.setName("test");
        createdEmployee.setDepartment("dept");

        // Mocking the employeeService behaviour
        Mockito.when(employeeService.createEmployee(employeeDTO)).thenReturn(createdEmployee);

        // Act
        ResponseEntity<EmployeeDTO> responseEntity = employeeController.createEmployee(employeeDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(createdEmployee, responseEntity.getBody());

    }

    @Test
    @DisplayName("JUnit test case for getEmployeeById")
    void testGetEmployeeById() throws Exception {
        Long id = 1L;
        EmployeeDTO mockEmployeeDTO = new EmployeeDTO();
        mockEmployeeDTO.setId(id);
        mockEmployeeDTO.setName("user1");
        mockEmployeeDTO.setDepartment("IT");

        Mockito.when(employeeService.getEmployeeById(id)).thenReturn(mockEmployeeDTO);

        // Act
        ResponseEntity<EmployeeDTO> responseEntity = employeeController.getEmployeeById(id);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockEmployeeDTO, responseEntity.getBody());
    }

    @Test
    @DisplayName("JUnit test case for getEmployeeById EmployeeNotFoundException")
    void testGetEmployeeByIdEmployeeNotFoundException() throws EmployeeNotFoundException {
        // Arrange
        Long id = 1L;

        // Mocking the service to throw an EmployeeNotFoundException
        Mockito.when(employeeService.getEmployeeById(id)).thenThrow(new EmployeeNotFoundException("Employee not found"));

        // Act and Assert
        assertThrows(EmployeeNotFoundException.class, () -> employeeController.getEmployeeById(id));
    }

    @Test
    @DisplayName("JUnit test case for getAllEmployees")
    void testGetAllEmployees() {

        // Arrange
        List<EmployeeDTO> mockEmployeeDTOs = new ArrayList<>();
        EmployeeDTO employee1 = new EmployeeDTO();
        employee1.setId(1L);
        employee1.setName("user1");
        employee1.setDepartment("IT");
        EmployeeDTO employee2 = new EmployeeDTO();
        employee2.setId(2L);
        employee2.setName("user2");
        employee2.setDepartment("HR");
        mockEmployeeDTOs.add(employee1);
        mockEmployeeDTOs.add(employee2);

        Mockito.when(employeeService.getAllEmployees()).thenReturn(mockEmployeeDTOs);

        // Act
        ResponseEntity<List<EmployeeDTO>> responseEntity = employeeController.getAllEmployees();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockEmployeeDTOs, responseEntity.getBody());
    }


    @Test
    @DisplayName("JUnit test case for updateEmployee")
    void testUpdateEmployee() throws EmployeeNotFoundException {
        // Arrange
        Long id = 1L;
        EmployeeDTO mockEmployeeDTO = new EmployeeDTO();
        mockEmployeeDTO.setId(id);
        mockEmployeeDTO.setName("Updated Employee");
        mockEmployeeDTO.setDepartment("Updated Department");

        EmployeeDTO updatedEmployee = new EmployeeDTO();
        updatedEmployee.setId(id);
        updatedEmployee.setName("Updated Employee");
        updatedEmployee.setDepartment("Updated Department");

        Mockito.when(employeeService.updateEmployee(id, mockEmployeeDTO)).thenReturn(updatedEmployee);

        // Act
        ResponseEntity<EmployeeDTO> responseEntity = employeeController.updateEmployee(id, mockEmployeeDTO);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedEmployee, responseEntity.getBody());
    }

    @Test
    @DisplayName("JUnit test case for updateEmployee EmployeeNotFoundException")
    void testUpdateEmployeeEmployeeNotFoundException() throws EmployeeNotFoundException {
        // Arrange
        Long id = 1L;
        EmployeeDTO mockEmployeeDTO = new EmployeeDTO();
        mockEmployeeDTO.setId(id);
        mockEmployeeDTO.setName("Updated Employee");
        mockEmployeeDTO.setDepartment("Updated Department");

        // Mocking the service to throw an EmployeeNotFoundException
        Mockito.when(employeeService.updateEmployee(id, mockEmployeeDTO)).thenThrow(new EmployeeNotFoundException("Employee not found"));

        // Act and Assert
        assertThrows(EmployeeNotFoundException.class, () -> employeeController.updateEmployee(id, mockEmployeeDTO));
    }


    @Test
    @DisplayName("JUnit test case for deleteEmployee")
    void testDeleteEmployee() throws EmployeeNotFoundException {
        // Arrange
        Long id = 1L;

        // Mocking the service to return without throwing an exception
        Mockito.doNothing().when(employeeService).deleteEmployee(id);

        // Act
        ResponseEntity<String> responseEntity = employeeController.deleteEmployee(id);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Employee deleted successfully", responseEntity.getBody());
    }

    @Test
    @DisplayName("JUnit test case for deleteEmployee EmployeeNotFoundException")
    void testDeleteEmployeeEmployeeNotFoundException() throws EmployeeNotFoundException {
        // Arrange
        Long id = 1L;

        // Mocking the service to throw an EmployeeNotFoundException
        Mockito.doThrow(new EmployeeNotFoundException("Employee not found")).when(employeeService).deleteEmployee(id);

        // Act and Assert
        assertThrows(EmployeeNotFoundException.class, () -> employeeController.deleteEmployee(id));
    }
}

