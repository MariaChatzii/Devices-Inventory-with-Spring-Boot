package com.inventory.deviceInventory.service;

import com.inventory.deviceInventory.DTO.DeviceDTO;
import com.inventory.deviceInventory.DTO.EmployeeDTO;
import com.inventory.deviceInventory.entity.Employee;
import com.inventory.deviceInventory.mapper.EmployeeEmployeeDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.inventory.deviceInventory.repository.EmployeeRepository;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeEmployeeDTOMapper employeeEmployeeDTOMapper;


    public List<EmployeeDTO> getEmployeesDTO(){
        return employeeEmployeeDTOMapper.employeesToEmployeeDTOs(employeeRepository.findAll());
    }

    public EmployeeDTO getEmployeeById(int id){
        return employeeEmployeeDTOMapper.employeeToEmployeeDTO(employeeRepository.findById(id).orElse(null));
    }

    public String deleteEmployee(int id){
        employeeRepository.deleteById(id);
        return "Employee with id = " + id + " is successfully removed!";
    }

    public EmployeeDTO updateEmployeeDTO(Employee employee){
        return employeeEmployeeDTOMapper.employeeToEmployeeDTO(employeeRepository.save(employee));
    }

    public List<EmployeeDTO> updateEmployeesDTO(List<Employee> employees){
        return employeeEmployeeDTOMapper.employeesToEmployeeDTOs(employeeRepository.saveAll(employees));
    }

    public EmployeeDTO getEmployeeDTOByEmail(String email) {
        return employeeEmployeeDTOMapper.employeeToEmployeeDTO(employeeRepository.findByEmail(email));
    }

    public EmployeeDTO saveEmployeeDTO(Employee employee){
        return employeeEmployeeDTOMapper.employeeToEmployeeDTO(employeeRepository.save(employee));
    }

    public List<EmployeeDTO> saveEmployeesDTO(List<Employee> employees){
        return employeeEmployeeDTOMapper.employeesToEmployeeDTOs(employeeRepository.saveAll(employees));
    }

    public List<EmployeeDTO> getEmployeesDTOByName(String name){
            return employeeEmployeeDTOMapper.employeesToEmployeeDTOs(employeeRepository.findByName(name));
    }

    public List<EmployeeDTO> getEmployeesDTOByCompanyNameAndCompanyAddress(String companyName, String companyAddress){
        return employeeEmployeeDTOMapper.employeesToEmployeeDTOs(employeeRepository.findByCompanyNameAndCompanyAddress(companyName, companyAddress));
    }

}
