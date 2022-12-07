package com.tcs.registration.service;

import com.tcs.registration.exception.ResourceNotFoundException;
import com.tcs.registration.model.Employee;
import com.tcs.registration.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee findById(long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
        return employee;
    }


    public Employee createEmployee(Employee employee) throws ResourceNotFoundException {
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(long employeeId, Employee emp) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

        employee.setEmailId(emp.getEmailId());
        employee.setLastName(emp.getLastName());
        employee.setFirstName(emp.getFirstName());
        final Employee updatedEmployee = employeeRepository.save(employee);
        return updatedEmployee;
    }

    public void deleteEmployee(long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
        employeeRepository.delete(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
}
