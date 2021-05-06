package com.inventory.deviceInventory.service;

import com.inventory.deviceInventory.DTO.EmployeeDTO;
import com.inventory.deviceInventory.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.inventory.deviceInventory.repository.EmployeeRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<Employee> getEmployeesByName(String name){
        return employeeRepository.findByName(name);
    }

    public Employee getEmployeeByEmail(String email){
        return employeeRepository.findByEmail(email);
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



    public EmployeeDTO getEmployeeDTOByEmail(String email){
        Employee employee = getEmployeeByEmail(email);

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setName(employee.getName());
        employeeDTO.setEmail(email);
        employeeDTO.setDevices(employee.getDevices());
        employeeDTO.setDevicesCount(employee.getDevices().size());
        employeeDTO.setCompanyName(employee.getCompany().getName());
        employeeDTO.setCompanyAddress(employee.getCompany().getAddress());


        return employeeDTO;
    }

    public EmployeeDTO saveEmployeeDTO(Employee employee){

        employeeRepository.save(employee);

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setName(employee.getName());
        employeeDTO.setEmail(employee.getEmail());
        employeeDTO.setDevices(Collections.emptyList());
        employeeDTO.setDevicesCount(0); //creating employee does not have any devices yet
        employeeDTO.setCompanyName(employee.getCompany().getName());
        employeeDTO.setCompanyAddress(employee.getCompany().getAddress());

        return employeeDTO;
    }

    public List<EmployeeDTO> getEmployeesDTOByName(String name){

        return getEmployeesByName(name).stream().map(employee -> {
            EmployeeDTO employeeDTO = new EmployeeDTO();
            employeeDTO.setName(name);
            employeeDTO.setEmail(employee.getEmail());
            employeeDTO.setDevices(employee.getDevices());
            employeeDTO.setDevicesCount(employee.getDevices().size());
            employeeDTO.setCompanyName(employee.getCompany().getName());
            employeeDTO.setCompanyAddress(employee.getCompany().getAddress());
            return employeeDTO;
        }).collect(Collectors.toList());
    }

    public List<EmployeeDTO> getEmployeesDTOByCompanyName(String name){

        return getEmployeesByCompanyName(name).stream().map(employee -> {
            EmployeeDTO employeeDTO = new EmployeeDTO();
            employeeDTO.setName(employee.getName());
            employeeDTO.setEmail(employee.getEmail());
            employeeDTO.setDevices(employee.getDevices());
            employeeDTO.setDevicesCount(employee.getDevices().size());
            employeeDTO.setCompanyName(name);
            employeeDTO.setCompanyAddress(employee.getCompany().getAddress());
            return employeeDTO;
        }).collect(Collectors.toList());
    }

    public List<EmployeeDTO> getEmployeesDTOByCompanyAddress(String address){

        return getEmployeesByCompanyAddress(address).stream().map(employee -> {
            EmployeeDTO employeeDTO = new EmployeeDTO();
            employeeDTO.setName(employee.getName());
            employeeDTO.setEmail(employee.getEmail());
            employeeDTO.setDevices(employee.getDevices());
            employeeDTO.setDevicesCount(employee.getDevices().size());
            employeeDTO.setCompanyName(employee.getCompany().getName());
            employeeDTO.setCompanyAddress(address);
            return employeeDTO;
        }).collect(Collectors.toList());
    }

    public  List<Employee> getEmployeesByCompanyId(Integer companyId){
        return employeeRepository.findByCompanyId(companyId);
    }

    public  List<Employee> getEmployeesByCompanyName(String companyName){
        return employeeRepository.findByCompanyName(companyName);
    }

    public  List<Employee> getEmployeesByCompanyAddress(String companyAddress){
        return employeeRepository.findByCompanyAddress(companyAddress);
    }
}
