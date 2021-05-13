package com.inventory.deviceInventory.service;

import com.inventory.deviceInventory.DTO.EmployeeDTO;
import com.inventory.deviceInventory.entity.Employee;
import com.inventory.deviceInventory.mapper.DTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.inventory.deviceInventory.repository.EmployeeRepository;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DTOMapper dtoMapper;


    public List<EmployeeDTO> getEmployeesDTO(){
        return dtoMapper.employeesToEmployeeDTOs(employeeRepository.findAll());
    }

    public EmployeeDTO getEmployeeById(int id){
        return dtoMapper.employeeToEmployeeDTO(employeeRepository.findById(id).orElse(null));
    }

    public EmployeeDTO getEmployeeDTOByEmail(String email) {
        return dtoMapper.employeeToEmployeeDTO(employeeRepository.findByEmail(email));
    }

    public List<EmployeeDTO> getEmployeesDTOByName(String name){
        return dtoMapper.employeesToEmployeeDTOs(employeeRepository.findByName(name));
    }

    public List<EmployeeDTO> getEmployeesDTOByCompanyName(String companyName){
        return dtoMapper.employeesToEmployeeDTOs(employeeRepository.findByCompanyName(companyName));
    }

    public List<EmployeeDTO> getEmployeesDTOByCompanyAddress(String companyAddress){
        return dtoMapper.employeesToEmployeeDTOs(employeeRepository.findByCompanyAddress(companyAddress));
    }

    public List<EmployeeDTO> getEmployeesDTOByCompanyNameAndCompanyAddress(String companyName, String companyAddress){
        return dtoMapper.employeesToEmployeeDTOs(employeeRepository.findByCompanyNameAndCompanyAddress(companyName, companyAddress));
    }

    public EmployeeDTO saveEmployeeDTO(Employee employee){
        return dtoMapper.employeeToEmployeeDTO(employeeRepository.save(employee));
    }

    public List<EmployeeDTO> saveEmployeesDTO(List<Employee> employees){
        return dtoMapper.employeesToEmployeeDTOs(employeeRepository.saveAll(employees));
    }

    public EmployeeDTO updateEmployeeDTO(Employee employee){
        Employee existingEmployee = employeeRepository.findById(employee.getId()).orElse(null);
        if(existingEmployee == null)
            return null;
        else
            return dtoMapper.employeeToEmployeeDTO(employeeRepository.save(employee));
    }

    public List<EmployeeDTO> updateEmployeesDTO(List<Employee> employees){
        for (Employee employee : employees) {
            Employee existingEmployee = employeeRepository.findById(employee.getId()).orElse(null);
            if(existingEmployee == null)
                return null;
        }
        return dtoMapper.employeesToEmployeeDTOs(employeeRepository.saveAll(employees));
    }

    public String deleteEmployeeById(int id){
        employeeRepository.deleteById(id);
        return "Employee with id: " + id + " is successfully removed!";
    }

}
