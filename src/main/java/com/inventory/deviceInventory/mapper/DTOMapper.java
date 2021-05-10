package com.inventory.deviceInventory.mapper;

import com.inventory.deviceInventory.DTO.DeviceDTO;
import com.inventory.deviceInventory.DTO.EmployeeDTO;
import com.inventory.deviceInventory.entity.Device;
import com.inventory.deviceInventory.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DTOMapper {
    //@Mapping(expression = "java(employee.getDevices().size())", target = "devicesCount")
    @Mapping(source = "employee.company.name", target = "companyName")
    @Mapping(source = "employee.company.address", target = "companyAddress")
    EmployeeDTO employeeToEmployeeDTO(Employee employee);
    List<EmployeeDTO> employeesToEmployeeDTOs(List<Employee> employees);

    @Mapping(source = "device.companyOwner.name", target = "companyOwnerName")
    @Mapping(source = "device.companyOwner.address", target = "companyOwnerAddress")
    @Mapping(source = "device.employeeOwner.name", target = "employeeOwnerName")
    @Mapping(source = "device.employeeOwner.email", target = "employeeOwnerEmail")
    DeviceDTO deviceToDeviceDTO(Device device);
    List<DeviceDTO> devicesToDeviceDTOs(List<Device> devices);
}
