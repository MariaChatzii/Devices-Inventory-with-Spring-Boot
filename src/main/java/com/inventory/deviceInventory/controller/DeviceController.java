package com.inventory.deviceInventory.controller;

import com.inventory.deviceInventory.DTO.DeviceDTO;
import com.inventory.deviceInventory.entity.Device;
import com.inventory.deviceInventory.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/device")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @GetMapping("/all")
    public List<DeviceDTO> findAllDevices(){
        return deviceService.getDevicesDTO();
    }

    @GetMapping("/{serialNumber}")
    public DeviceDTO findDeviceDTOBySerialNumber(@PathVariable String serialNumber){
        return deviceService.getDeviceDTOBySerialNumber(serialNumber);
    }

    @GetMapping
    public List<DeviceDTO> findDevicesDTOByNameAndType(@RequestParam String name, @RequestParam String type){
        return deviceService.getDevicesDTOByNameAndType(name, type);
    }

    @GetMapping("/company")
    public List<DeviceDTO> findDevicesDTOByCompanyNameAndCompanyAddress(@RequestParam String companyName, @RequestParam String companyAddress){
        return deviceService.getDevicesDTOByCompanyNameAndCompanyAddress(companyName, companyAddress);
    }

    @PostMapping("/add")
    public DeviceDTO addDevice(@RequestBody Device device){
        return deviceService.saveDevice(device);
    }

    @PostMapping("/addMany")
    public List<DeviceDTO> addDevices(@RequestBody List<Device> devices){
        return deviceService.saveDevices(devices);
    }

    @PutMapping("/update")
    public DeviceDTO updateDevice(@RequestBody Device device){
        return deviceService.updateDevice(device);
    }

    @PutMapping("/updateMany")
    public List<DeviceDTO> updateDevices(@RequestBody List<Device> devices){
        return deviceService.updateDevices(devices);
    }

    @DeleteMapping("/delete/{serialNumber}")
    public String deleteDevice(@PathVariable String serialNumber){
        return deviceService.deleteDeviceBySerialNumber(serialNumber);
    }






}
