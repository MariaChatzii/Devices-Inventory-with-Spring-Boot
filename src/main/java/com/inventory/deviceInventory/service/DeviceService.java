package com.inventory.deviceInventory.service;

import com.inventory.deviceInventory.entity.Device;
import com.inventory.deviceInventory.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    public Device saveDevice(Device device) {
        return deviceRepository.save(device);
    }

    public List<Device> saveDevices(List<Device> devices) {
        return deviceRepository.saveAll(devices);
    }

    public Device getDeviceBySerialNumber(String serial_number) {
        return deviceRepository.findById(serial_number).orElse(null);
    }

    public List<Device> getDevices() {
        return deviceRepository.findAll();
    }

    public String deleteDeviceBySerialNumber(String serial_number) {
        deviceRepository.deleteById(serial_number);
        return "Device with serial_number = " + serial_number + " is successfully removed!";
    }

    public Device updateDevice(Device device) {
        Device selectedDevice = deviceRepository.findById(device.getSerialNumber()).orElse(null);
        if (selectedDevice != null) {
            selectedDevice.setName(device.getName());
            selectedDevice.setType(device.getType());
            selectedDevice.setCompanyOwner(device.getCompanyOwner());
            selectedDevice.setEmployeeOwner(device.getEmployeeOwner());
            return deviceRepository.save(selectedDevice);
        }
        return null;
    }

    public List<Device> updateDevices(List<Device> devices) {

        ArrayList<Device> selectedDevices = new ArrayList<>();
        for (Device device : devices) {
            Device selectedDevice = deviceRepository.findById(device.getSerialNumber()).orElse(null);
            if (selectedDevice != null) {
                selectedDevice.setName(device.getName());
                selectedDevice.setType(device.getType());
                selectedDevice.setCompanyOwner(device.getCompanyOwner());
                selectedDevice.setEmployeeOwner(device.getEmployeeOwner());
                selectedDevices.add(selectedDevice);
            } else {
                return null;
            }
        }
        return deviceRepository.saveAll(selectedDevices);

    }


}
