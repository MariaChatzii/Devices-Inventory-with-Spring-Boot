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

    public String deleteEmployee(int id){
        employeeRepository.deleteById(id);
        return "Employee with id = " + id + " is successfully removed!";
    }

    public EmployeeDTO updateEmployeeDTO(Employee employee){
        return dtoMapper.employeeToEmployeeDTO(employeeRepository.save(employee));
    }

    public List<EmployeeDTO> updateEmployeesDTO(List<Employee> employees){
        return dtoMapper.employeesToEmployeeDTOs(employeeRepository.saveAll(employees));
    }

    public EmployeeDTO getEmployeeDTOByEmail(String email) {
        return dtoMapper.employeeToEmployeeDTO(employeeRepository.findByEmail(email));
    }

    public EmployeeDTO saveEmployeeDTO(Employee employee){
        return dtoMapper.employeeToEmployeeDTO(employeeRepository.save(employee));
    }

    public List<EmployeeDTO> saveEmployeesDTO(List<Employee> employees){
        return dtoMapper.employeesToEmployeeDTOs(employeeRepository.saveAll(employees));
    }

    public List<EmployeeDTO> getEmployeesDTOByName(String name){
            return dtoMapper.employeesToEmployeeDTOs(employeeRepository.findByName(name));
    }

    public List<EmployeeDTO> getEmployeesDTOByCompanyNameAndCompanyAddress(String companyName, String companyAddress){
        return dtoMapper.employeesToEmployeeDTOs(employeeRepository.findByCompanyNameAndCompanyAddress(companyName, companyAddress));
    }

}
