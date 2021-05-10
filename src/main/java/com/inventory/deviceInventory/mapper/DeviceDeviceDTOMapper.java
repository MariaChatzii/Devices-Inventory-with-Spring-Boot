package com.inventory.deviceInventory.mapper;

import com.inventory.deviceInventory.DTO.DeviceDTO;
import com.inventory.deviceInventory.entity.Device;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DeviceDeviceDTOMapper {
    @Mapping(source = "device.companyOwner.name", target = "companyOwnerName")
    @Mapping(source = "device.companyOwner.address", target = "companyOwnerAddress")
    @Mapping(source = "device.employeeOwner.name", target = "employeeOwnerName")
    @Mapping(source = "device.employeeOwner.email", target = "employeeOwnerEmail")
    DeviceDTO deviceToDeviceDTO(Device device);
    List<DeviceDTO> devicesToDeviceDTOs(List<Device> devices);
}
