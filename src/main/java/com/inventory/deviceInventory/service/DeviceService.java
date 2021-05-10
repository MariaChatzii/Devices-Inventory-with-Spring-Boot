package com.inventory.deviceInventory.service;

import com.inventory.deviceInventory.DTO.DeviceDTO;
import com.inventory.deviceInventory.DTO.EmployeeDTO;
import com.inventory.deviceInventory.entity.Device;
import com.inventory.deviceInventory.entity.Employee;
import com.inventory.deviceInventory.mapper.DeviceDeviceDTOMapper;
import com.inventory.deviceInventory.mapper.EmployeeEmployeeDTOMapper;
import com.inventory.deviceInventory.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private DeviceDeviceDTOMapper deviceDeviceDTOMapper;

    public DeviceDTO getDeviceDTOBySerialNumber(String serialNumber){

        return deviceDeviceDTOMapper.deviceToDeviceDTO(deviceRepository.findById(serialNumber).orElse(null));
    }

    public List<DeviceDTO> getDevicesDTO() {
        return deviceDeviceDTOMapper.devicesToDeviceDTOs(deviceRepository.findAll());
    }

    public List<DeviceDTO> getDevicesDTOByCompanyNameAndCompanyAddress(String companyName, String companyAddress){
        return deviceDeviceDTOMapper.devicesToDeviceDTOs(deviceRepository.findByCompanyOwnerNameAndCompanyOwnerAddress(companyName, companyAddress));
    }

    public List<DeviceDTO> getDevicesDTOByNameAndType(String name, String type){
        return deviceDeviceDTOMapper.devicesToDeviceDTOs(deviceRepository.findByNameAndType(name, type));
    }

    public DeviceDTO saveDevice(Device device) {
        return deviceDeviceDTOMapper.deviceToDeviceDTO(deviceRepository.save(device));
    }

    public List<DeviceDTO> saveDevices(List<Device> devices) {
        return deviceDeviceDTOMapper.devicesToDeviceDTOs(deviceRepository.saveAll(devices));
    }

    public DeviceDTO updateDevice(Device device) {
        return deviceDeviceDTOMapper.deviceToDeviceDTO(deviceRepository.save(device));
    }

    public List<DeviceDTO> updateDevices(List<Device> devices) {
        return deviceDeviceDTOMapper.devicesToDeviceDTOs(deviceRepository.saveAll(devices));
    }

    public String deleteDeviceBySerialNumber(String serial_number) {
        deviceRepository.deleteById(serial_number);
        return "Device with serial_number = " + serial_number + " is successfully removed!";
    }

}
