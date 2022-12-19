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
public class EmployeeService implements IEmployeeService{
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee findById(long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
        return employee;
    }

    @Override
    public Employee findByEmailId(String emailId) {
        Employee employee = employeeRepository.findByEmailId(emailId)
                .orElse(null);
        return employee;
    }


    @Override
    public Employee createEmployee(Employee employee) throws ResourceNotFoundException {
//        employeeRepository.findByEmailId(employee.getEmailId()).
//                orElseThrow(() -> new ResourceNotFoundException("Employee already exist for this email :: " + employee.getEmailId()));
//        Optional<Employee> employeeOptiona = employeeRepository.findByEmailId(employee.getEmailId());
//        findById()
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(long employeeId, Employee emp) {
        Employee employee = findById(employeeId);
        employee.setEmailId(emp.getEmailId());
        employee.setLastName(emp.getLastName());
        employee.setFirstName(emp.getFirstName());
        final Employee updatedEmployee = employeeRepository.save(employee);
        return updatedEmployee;
    }

    @Override
    public void deleteEmployee(long employeeId) {
        Employee employee = findById(employeeId);
        employeeRepository.delete(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
}
