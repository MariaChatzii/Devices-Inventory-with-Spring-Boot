package com.inventory.deviceInventory.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.inventory.deviceInventory.DTO.EmployeeDTO;
import com.inventory.deviceInventory.View;
import com.inventory.deviceInventory.entity.Employee;
import com.inventory.deviceInventory.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/employee")
public class EmployeeController {

    @Autowired
    private final EmployeeService employeeService;


    @GetMapping("/all")
    @JsonView(View.WithoutDeviceCompanyAndEmployeeInfo.class)
    public List<EmployeeDTO> findAllEmployees(){
        return  employeeService.getEmployeesDTO();
    }

    @GetMapping("/{id}")
    @JsonView(View.WithoutDeviceCompanyAndEmployeeInfo.class)
    public EmployeeDTO findEmployeeById(@PathVariable int id){
        return employeeService.getEmployeeById(id);
    }

    @GetMapping("/email/{email}")
    @JsonView(View.WithoutDeviceCompanyAndEmployeeInfo.class)
    public EmployeeDTO findEmployeeDTOByEmail(@PathVariable String email){
        return employeeService.getEmployeeDTOByEmail(email);
    }

    @GetMapping("/name/{name}")
    @JsonView(View.WithoutDeviceCompanyAndEmployeeInfo.class)
    public List<EmployeeDTO> findEmployeesDTOByName(@PathVariable String name){
        return employeeService.getEmployeesDTOByName(name);
    }

    @GetMapping("/company")
    @JsonView(View.WithoutDeviceCompanyAndEmployeeInfo.class)
    public List<EmployeeDTO> findEmployeesDTOByCompanyNameAndCompanyAddress(@RequestParam(required = false) String companyName , @RequestParam(required = false) String companyAddress){
        if (companyName != null && companyAddress != null)
            return employeeService.getEmployeesDTOByCompanyNameAndCompanyAddress(companyName, companyAddress);
        else if (companyName != null)
            return employeeService.getEmployeesDTOByCompanyName(companyName);
        else if (companyAddress != null)
            return employeeService.getEmployeesDTOByCompanyAddress(companyAddress);
        else
            return employeeService.getEmployeesDTO();
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addEmployee(@RequestBody Employee employee){
        if(employeeService.getEmployeeDTOByEmail(employee.getEmail()) != null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Employee with mail: " + employee.getEmail() + " already exists!");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.saveEmployeeDTO(employee));
    }

    @PostMapping("/addMany")
    public ResponseEntity<Object> addEmployees(@RequestBody List<Employee> employees){
        for(Employee employee : employees) {
            if (employeeService.getEmployeeDTOByEmail(employee.getEmail()) != null){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Employee with mail: " + employee.getEmail() + " already exists!");
            }
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.saveEmployeesDTO(employees));
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateEmployee(@RequestBody Employee employee) {
       EmployeeDTO employeeDTO = employeeService.updateEmployeeDTO(employee);
       if(employeeDTO == null)
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Employee with id: " + employee.getId() + " does not exist!");

        return ResponseEntity.status(HttpStatus.OK).body(employeeDTO);
    }

    @PutMapping("/updateMany")
    public ResponseEntity<Object> updateEmployees(@RequestBody List<Employee> employees) {
        List<EmployeeDTO> employeesDTO = employeeService.updateEmployeesDTO(employees);
        if(employeesDTO == null)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("At least one of the employees does not exist!");

        return ResponseEntity.status(HttpStatus.OK).body(employeesDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable int id){
        if(employeeService.getEmployeeById(id) == null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Employee with id: " + id + " does not exist!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.deleteEmployeeById(id));
    }

}
