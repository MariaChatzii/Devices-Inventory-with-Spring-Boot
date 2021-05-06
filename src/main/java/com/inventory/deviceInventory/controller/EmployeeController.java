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
    private EmployeeService employeeService;

   @PostMapping("/addEmployee")
    public Employee addEmployee(@RequestBody Employee employee){
        return employeeService.saveEmployee(employee);
    }

    /*
    @PostMapping("/addMany")
    public List<Employee> addEmployees(@RequestBody List<Employee> employees){
        return employeeService.saveEmployees(employees);
    }*/

    @GetMapping("/all")
    public List<Employee> findAllEmployees(){
        return  employeeService.getEmployees();
    }

    @GetMapping("/{id}")
    public Employee findEmployee(@PathVariable int id){
        return employeeService.getEmployeeById(id);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable int id){
        return employeeService.deleteEmployee(id);
    }

    @PutMapping("/update")
    public Employee updateEmployee(@RequestBody Employee employee) {
        return employeeService.updateEmployee(employee);
    }


    @GetMapping("/email/{email}")
    public Employee findEmployeeByEmail(@PathVariable String email){
        return employeeService.getEmployeeByEmail(email);
    }

    @GetMapping("/dtoemail/{email}")
    public EmployeeDTO findEmployeeDTOByEmail(@PathVariable String email){
        return employeeService.getEmployeeDTOByEmail(email);
    }

    @GetMapping("/dtoname/{name}")
    public List<EmployeeDTO> findEmployeeDTOByName(@PathVariable String name){
        return employeeService.getEmployeesDTOByName(name);
    }

    @GetMapping
    public List<EmployeeDTO> findEmployeeDTOByCompanyNameAndAddress(@RequestParam String companyName, @RequestParam String companyAddress){
        List<EmployeeDTO> byCompany = employeeService.getEmployeesDTOByCompanyName(companyName);
        byCompany.retainAll(employeeService.getEmployeesDTOByCompanyAddress(companyAddress));
        return byCompany;
    }

    @GetMapping("/dtocomp/{companyName}")
    public  List<EmployeeDTO> findEmployeeDTOByCompanyName(@PathVariable String companyName){
        return employeeService.getEmployeesDTOByCompanyName(companyName);
    }


    @GetMapping("/comid/{companyId}")
    public  List<Employee> findEmployeeDTOByCompanyId(@PathVariable Integer companyId){
        return employeeService.getEmployeesByCompanyId(companyId);
    }

    @PostMapping("/add")
    public EmployeeDTO saveEmployeeDTO(@RequestBody Employee employee){
        return employeeService.saveEmployeeDTO(employee);
    }


}
