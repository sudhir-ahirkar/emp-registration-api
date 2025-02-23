package com.tcs.registration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcs.registration.exception.ResourceNotFoundException;
import com.tcs.registration.model.Employee;
import com.tcs.registration.repository.EmployeeRepository;
import com.tcs.registration.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.doNothing;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * EmployeeControllerTests is a test class for testing the EmployeeController REST API endpoints.
 * It uses MockMvc for performing HTTP requests and validating responses.
 * The class is annotated with @WebMvcTest to focus on web layer testing.
 * 
 * The following tests are included:
 * 
 * 1. givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee1:
 *    Tests the creation of a new employee and verifies the saved employee object.
 * 
 * 2. givenListOfEmployees_whenGetAllEmployees_thenReturnEmployeesList1:
 *    Tests retrieving all employees and verifies the returned list of employees.
 * 
 * 3. givenListOfEmployees_whenGetAllEmployees_thenReturnEmployeesList:
 *    Tests retrieving all employees and verifies the returned list of employees using ResultActions.
 * 
 * 4. givenInvalidEmployeeId_whenGetEmployeeById_thenReturnEmpty:
 *    Tests retrieving an employee by an invalid ID and verifies that a ResourceNotFoundException is thrown.
 * 
 * 5. givenUpdatedEmployee_whenUpdateEmployee_thenReturnUpdateEmployeeObject:
 *    Tests updating an existing employee and verifies the updated employee object.
 * 
 * 6. givenUpdatedEmployee_whenUpdateEmployee_thenReturn404:
 *    Tests updating an employee with an invalid ID and verifies that a 404 Not Found status is returned.
 * 
 * 7. givenEmployeeId_whenDeleteEmployee_thenReturn2001:
 *    Tests deleting an employee by ID and verifies that a 200 OK status is returned.
 * 
 * The class uses @MockBean to mock the EmployeeService and EmployeeRepository dependencies.
 * The ObjectMapper is used for converting objects to JSON strings.
 */
@WebMvcTest
public class EmployeeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private EmployeeRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee1() throws Exception {
        //q: how to fix this error? 
        Employee employee = Employee.builder()
                .id(1L)
                .firstName("Ramesh")
                .lastName("Fadatare")
                .emailId("ramesh@gmail.com")
                .build();

        Mockito.when(employeeService.createEmployee(any(Employee.class))).thenReturn(employee);

        String expected = objectMapper.writeValueAsString(employee);

        // Send course as body to /students/Student1/courses
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/employees")
                .accept(MediaType.APPLICATION_JSON).content(expected)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }


    @Test
    public void givenListOfEmployees_whenGetAllEmployees_thenReturnEmployeesList1() throws Exception {
        // given - precondition or setup
        List<Employee> listOfEmployees = new ArrayList<>();
        listOfEmployees.add(Employee.builder().id(1L).firstName("Ramesh").lastName("Fadatare").emailId("ramesh@gmail.com").build());
        listOfEmployees.add(Employee.builder().id(2L).firstName("Tony").lastName("Stark").emailId("tony@gmail.com").build());

        Mockito.when(employeeService.getAllEmployees()).thenReturn(listOfEmployees);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/api/v1/employees").accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expected = objectMapper.writeValueAsString(listOfEmployees);
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);

    }

    // JUnit test for Get All employees REST API
    @Test
    public void givenListOfEmployees_whenGetAllEmployees_thenReturnEmployeesList() throws Exception {
        // given - precondition or setup
        List<Employee> listOfEmployees = new ArrayList<>();
        //Q:How do I fix this builder error?
        listOfEmployees.add(Employee.builder().firstName("Ramesh").lastName("Fadatare").emailId("ramesh@gmail.com").build());
        listOfEmployees.add(Employee.builder().firstName("Tony").lastName("Stark").emailId("tony@gmail.com").build());
        given(employeeService.getAllEmployees()).willReturn(listOfEmployees);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/v1/employees"));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfEmployees.size())));
    }


    // negative scenario - valid employee id
    // JUnit test for GET employee by id REST API
    @Test
    public void givenInvalidEmployeeId_whenGetEmployeeById_thenReturnEmpty() throws Exception {
        // given - precondition or setup
        long employeeId = 1L;
        Employee employee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Fadatare")
                .emailId("ramesh@gmail.com")
                .build();
        given(employeeService.findById(employeeId)).willThrow(new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/v1/employees/{id}", employeeId));
        // then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    // JUnit test for update employee REST API - positive scenario
    @Test
    public void givenUpdatedEmployee_whenUpdateEmployee_thenReturnUpdateEmployeeObject() throws Exception {
        Employee updatedEmployee = Employee.builder()
                .id(1L)
                .firstName("Ram")
                .lastName("Jadhav")
                .emailId("ram@gmail.com")
                .build();
        Mockito.when(employeeService.updateEmployee(Mockito.anyLong(), any(Employee.class))).thenReturn(updatedEmployee);
        String expected = objectMapper.writeValueAsString(updatedEmployee);
        // Send course as body to /students/Student1/courses
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/api/v1/employees/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON).content(expected)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    // JUnit test for update employee REST API - negative scenario
    @Test
    public void givenUpdatedEmployee_whenUpdateEmployee_thenReturn404() throws Exception {
        Employee updatedEmployee = Employee.builder()
                .id(1L)
                .firstName("Ram")
                .lastName("Jadhav")
                .emailId("ram@gmail.com")
                .build();
        Mockito.when(employeeService.updateEmployee(Mockito.anyLong(), any(Employee.class))).thenThrow(new ResourceNotFoundException("Resource not found to update "));
        String expected = objectMapper.writeValueAsString(updatedEmployee);
        // Send course as body to /students/Student1/courses
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/api/v1/employees/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON).content(expected)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder).andExpect(status().isNotFound())
                .andDo(print());
    }

    // JUnit test for delete employee REST API
    
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenReturn2001() throws Exception {
        doNothing().when(employeeService).deleteEmployee(Mockito.anyLong());
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/v1/employees/{id}", 1L).contentType(MediaType.APPLICATION_JSON);
        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(requestBuilder).andExpect(status().isOk());
        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());
    }
}
