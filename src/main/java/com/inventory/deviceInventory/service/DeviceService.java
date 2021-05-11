package com.inventory.deviceInventory.service;

import com.inventory.deviceInventory.DTO.DeviceDTO;
import com.inventory.deviceInventory.entity.Device;
import com.inventory.deviceInventory.mapper.DTOMapper;
import com.inventory.deviceInventory.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private DTOMapper dtoMapper;


    public DeviceDTO getDeviceDTOBySerialNumber(String serialNumber){
        return dtoMapper.deviceToDeviceDTO(deviceRepository.findById(serialNumber).orElse(null));
    }

    public List<DeviceDTO> getDevicesDTO() {
        return dtoMapper.devicesToDeviceDTOs(deviceRepository.findAll());
    }

    public List<DeviceDTO> getDevicesDTOByCompanyName(String companyName){
        return dtoMapper.devicesToDeviceDTOs(deviceRepository.findByCompanyOwnerName(companyName));
    }

    public List<DeviceDTO> getDevicesDTOByCompanyAddress(String companyAddress){
        return dtoMapper.devicesToDeviceDTOs(deviceRepository.findByCompanyOwnerAddress(companyAddress));
    }

    public List<DeviceDTO> getDevicesDTOByCompanyNameAndCompanyAddress(String companyName, String companyAddress){
        return dtoMapper.devicesToDeviceDTOs(deviceRepository.findByCompanyOwnerNameAndCompanyOwnerAddress(companyName, companyAddress));
    }

    public List<DeviceDTO> getDevicesDTOByNameAndType(String name, String type){
        return dtoMapper.devicesToDeviceDTOs(deviceRepository.findByNameAndType(name, type));
    }

    public DeviceDTO saveDeviceDTO(Device device) {
        return dtoMapper.deviceToDeviceDTO(deviceRepository.save(device));
    }

    public List<DeviceDTO> saveDevicesDTO(List<Device> devices) {
        return dtoMapper.devicesToDeviceDTOs(deviceRepository.saveAll(devices));
    }

    public DeviceDTO updateDeviceDTO(Device device) {
        return dtoMapper.deviceToDeviceDTO(deviceRepository.save(device));
    }

    public List<DeviceDTO> updateDevicesDTO(List<Device> devices) {
        return dtoMapper.devicesToDeviceDTOs(deviceRepository.saveAll(devices));
    }

    public String deleteDeviceBySerialNumber(String serial_number) {
        deviceRepository.deleteById(serial_number);
        return "Device with serial_number: " + serial_number + " is successfully removed!";
    }

}
