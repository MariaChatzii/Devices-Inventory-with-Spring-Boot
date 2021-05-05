package com.inventory.deviceInventory.controller;

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

    @PostMapping("/register")
    public Device addDevice(@RequestBody Device device){
        return deviceService.saveDevice(device);
    }

    @PostMapping("/registerMany")
    public List<Device> addDevices(@RequestBody List<Device> devices){
        return deviceService.saveDevices(devices);
    }

    @GetMapping("/all")
    public List<Device> findAllDevices(){
        return deviceService.getDevices();
    }

    @GetMapping("/{serialNumber}")
    public Device findDevice(@PathVariable String serialNumber){
        return deviceService.getDeviceBySerialNumber(serialNumber);
    }

    @DeleteMapping("/delete/{SerialNumber}")
    public String deleteEmployee(@PathVariable String serialNumber){
        return deviceService.deleteDeviceBySerialNumber(serialNumber);
    }

    @PutMapping("/update")
    public Device updateDevice(@RequestBody Device device){
        return deviceService.updateDevice(device);
    }

    @PutMapping("/updateMany")
    public List<Device> updateDevices(@RequestBody List<Device> devices){
        return deviceService.updateDevices(devices);
    }

}
