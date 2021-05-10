package com.inventory.deviceInventory.mapper;

import com.inventory.deviceInventory.DTO.EmployeeDTO;
import com.inventory.deviceInventory.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeEmployeeDTOMapper {
    @Mapping(source = "employee.company.name", target = "companyName")
    @Mapping(source = "employee.company.address", target = "companyAddress")
    @Mapping(expression = "java(employee.getDevices().size())", target = "devicesCount")
    EmployeeDTO employeeToEmployeeDTO(Employee employee);
    List<EmployeeDTO> employeesToEmployeeDTOs(List<Employee> employees);
}
