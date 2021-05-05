package com.inventory.deviceInventory.service;

import com.inventory.deviceInventory.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.inventory.deviceInventory.repository.EmployeeRepository;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee saveEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    public List<Employee> saveEmployees(List<Employee> employees){
        return  employeeRepository.saveAll(employees);
    }

    public List<Employee> getEmployees(){
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(int id){
        return employeeRepository.findById(id).orElse(null);
    }

    public String deleteEmployee(int id){
        employeeRepository.deleteById(id);
        return "Employee with id = " + id + " is successfully removed!";
    }

    public Employee updateEmployee(Employee employee){
        Employee selectedEmployee = employeeRepository.findById(employee.getId()).orElse(null);
        assert selectedEmployee != null;
        selectedEmployee.setName(employee.getName());
        selectedEmployee.setEmail(employee.getEmail());
        selectedEmployee.setCompany(employee.getCompany());
        return employeeRepository.save(selectedEmployee);
    }
}
