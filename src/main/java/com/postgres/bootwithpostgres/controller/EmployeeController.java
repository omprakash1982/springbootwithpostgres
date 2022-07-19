package com.postgres.bootwithpostgres.controller;

import com.postgres.bootwithpostgres.dao.EmployeeRepository;
import com.postgres.bootwithpostgres.entity.Employee;
import com.postgres.bootwithpostgres.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/v1")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;
//get all employee
    @GetMapping("employees")
    public List<Employee> getAllEmployee(){
        return this.employeeRepository.findAll();
    }
    //get employee by id
    @GetMapping("employee/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") Long employId) throws ResourceNotFoundException{
      Employee employee= employeeRepository.findById(employId)
              .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id"+employId));
        return ResponseEntity.ok().body(employee);
    }

    //save employee
    @PostMapping("/employees")
    public Employee saveEmployee(@RequestBody Employee employee){
       return this.employeeRepository.save(employee);
    }
    //update employee
@PutMapping("/employee/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long employeeId,
                                                   @Validated @RequestBody Employee employeeDetails) throws ResourceNotFoundException{
       Employee employee= this.employeeRepository.findById(employeeId)
                .orElseThrow(()-> new ResourceNotFoundException("employee not find by this id "+employeeId));
       employee.setFirstname(employeeDetails.getFirstname());
       employee.setLastname(employeeDetails.getLastname());
       employee.setEmail(employeeDetails.getEmail());
       return ResponseEntity.ok(this.employeeRepository.save(employee));
    }
    //delete employee
    public Map<String,Boolean> deleteEmployee(@PathVariable(value = "id") Long employId) throws ResourceNotFoundException{
        Employee employee= employeeRepository.findById(employId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id"+employId));
         this.employeeRepository.delete(employee);
Map<String, Boolean> response=new HashMap<>();
response.put("deleted",true);
return response;
    }

}
