package com.inventory.deviceInventory.controller;

import com.inventory.deviceInventory.DTO.DeviceDTO;
import com.inventory.deviceInventory.entity.Device;
import com.inventory.deviceInventory.service.DeviceService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/device")
public class DeviceController {

    @Autowired
    private final DeviceService deviceService;


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
    public ResponseEntity<Object> addDevice(@RequestBody Device device){
        if(deviceService.getDeviceDTOBySerialNumber(device.getSerialNumber()) != null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Device with serial number: " + device.getSerialNumber() + " already exists");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(deviceService.saveDeviceDTO(device));
    }

    @PostMapping("/addMany")
    public ResponseEntity<Object> addDevices(@RequestBody List<Device> devices){
        for(Device device : devices) {
            if (deviceService.getDeviceDTOBySerialNumber(device.getSerialNumber()) != null){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Device with serial number: " + device.getSerialNumber() + " already exists");
            }
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(deviceService.saveDevicesDTO(devices));
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateDevice(@RequestBody Device device){
        if(deviceService.getDeviceDTOBySerialNumber(device.getSerialNumber()) != null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Device with serial number: " + device.getSerialNumber() + " already exists");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(deviceService.updateDeviceDTO(device));
    }

    @PutMapping("/updateMany")
    public ResponseEntity<Object> updateDevices(@RequestBody List<Device> devices){
        for(Device device : devices) {
            if (deviceService.getDeviceDTOBySerialNumber(device.getSerialNumber()) != null){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Device with serial number: " + device.getSerialNumber() + " already exists");
            }
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(deviceService.updateDevicesDTO(devices));
    }

    @DeleteMapping("/delete/{serialNumber}")
    public ResponseEntity<Object> deleteDevice(@PathVariable String serialNumber){
        if(deviceService.getDeviceDTOBySerialNumber(serialNumber) == null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Device with serial number: " + serialNumber + " does not exist");
        }
        return ResponseEntity.status(HttpStatus.OK).body(deviceService.deleteDeviceBySerialNumber(serialNumber));
    }


}
