package com.tcs.registration.service;

import com.tcs.registration.model.Employee;

import java.util.List;

public interface IEmployeeService {

     Employee findById(long employeeId);
     Employee findByEmailId(String emailId);
     Employee createEmployee(Employee employee);
     Employee updateEmployee(long employeeId, Employee emp);
     void deleteEmployee(long employeeId);
     List<Employee> getAllEmployees();
}
