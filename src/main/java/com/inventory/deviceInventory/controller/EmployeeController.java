package com.inventory.deviceInventory.controller;

import com.inventory.deviceInventory.DTO.EmployeeDTO;
import com.inventory.deviceInventory.entity.Employee;
import com.inventory.deviceInventory.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/employee")
public class EmployeeController {

    @Autowired
    private final EmployeeService employeeService;

    @GetMapping("/all")
    public List<EmployeeDTO> findAllEmployees(){
        return  employeeService.getEmployeesDTO();
    }

    @GetMapping("/{id}")
    public EmployeeDTO findEmployee(@PathVariable int id){
        return employeeService.getEmployeeById(id);
    }

    @GetMapping("/email/{email}")
    public EmployeeDTO findEmployeeDTOByEmail(@PathVariable String email){
        return employeeService.getEmployeeDTOByEmail(email);
    }

    @GetMapping("/name/{name}")
    public List<EmployeeDTO> findEmployeeDTOByName(@PathVariable String name){
        return employeeService.getEmployeesDTOByName(name);
    }

    @GetMapping("/company")
    public List<EmployeeDTO> findEmployeesDTOByCompanyNameAndCompanyAddress(@RequestParam String companyName, @RequestParam String companyAddress){
        return employeeService.getEmployeesDTOByCompanyNameAndCompanyAddress(companyName, companyAddress);
    }

    @PostMapping("/add")
    public EmployeeDTO addEmployee(@RequestBody Employee employee){
        return employeeService.saveEmployeeDTO(employee);
    }

    @PostMapping("/addMany")
    public List<EmployeeDTO> addEmployees(@RequestBody List<Employee> employees){
        return employeeService.saveEmployeesDTO(employees);
    }

    @PutMapping("/update")
    public EmployeeDTO updateEmployee(@RequestBody Employee employee) {
        return employeeService.updateEmployeeDTO(employee);
    }

    @PutMapping("/updateMany")
    public List<EmployeeDTO> updateEmployees(@RequestBody List<Employee> employees) {
        return employeeService.updateEmployeesDTO(employees);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable int id){
        return employeeService.deleteEmployee(id);
    }

}
