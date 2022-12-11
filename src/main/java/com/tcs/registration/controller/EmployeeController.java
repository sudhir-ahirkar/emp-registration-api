package com.tcs.registration.controller;

import com.tcs.registration.model.Employee;
import com.tcs.registration.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
@Slf4j
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> empLst = employeeService.getAllEmployees();
        log.info("Employees has been fetched successfully");
        return ResponseEntity.ok().body(empLst);
    }

   /* @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmpById(@PathVariable(value = "id") Long employeeId) {
        Employee employee = employeeService.findById(employeeId);
        log.info("Employee {} has been fetched successfully",employeeId);
        return ResponseEntity.ok().body(employee);
    }*/

   /* @GetMapping("/employees/{emailId}")
    public ResponseEntity<Employee> getEmpByEmailId(@PathVariable(value = "emailId") String emailId) {
        Employee employee = employeeService.findByEmailId(emailId);
        log.info("Employee {} has been fetched successfully");
        return ResponseEntity.ok().body(employee);
    }*/

    @GetMapping("/employees/email")
    public ResponseEntity<Employee> getEmpByEmailId(@RequestParam(value = "emailId") String emailId) {
        Employee employee = employeeService.findByEmailId(emailId);
        log.info("Employee {} has been fetched successfully");
        return ResponseEntity.ok().body(employee);
    }

    @GetMapping("/employees/es")
    public ResponseEntity<Employee> getEmployeeById(@RequestParam(value = "id") Long employeeId) {
        Employee employee = employeeService.findById(employeeId);
        log.info("Employee {} has been fetched successfully",employeeId);
        return ResponseEntity.ok().body(employee);
    }

    @PostMapping("/employees")
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee) {
        Employee emp = employeeService.createEmployee(employee);
        log.info("Employee {} has been created successfully",emp);
        return new ResponseEntity<>(emp,HttpStatus.CREATED);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long employeeId, @Valid @RequestBody Employee employeeDetails) {
        final Employee updatedEmployee = employeeService.updateEmployee(employeeId, employeeDetails);
        log.info("Employee {} has been updated successfully",employeeId);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable(value = "id") Long employeeId) {
        employeeService.deleteEmployee(employeeId);
        log.info("Employee {} has been removed successfully",employeeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
