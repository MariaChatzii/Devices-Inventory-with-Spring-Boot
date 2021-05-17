package com.inventory.deviceInventory.controller;

import com.inventory.deviceInventory.DTO.DeviceDTO;
import com.inventory.deviceInventory.entity.Device;
import com.inventory.deviceInventory.entity.Employee;
import com.inventory.deviceInventory.repository.EmployeeRepository;
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

    @Autowired
    private final EmployeeRepository employeeRepository;


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
    public List<DeviceDTO> findDevicesDTOByCompanyNameAndCompanyAddress(@RequestParam (required = false) String companyName, @RequestParam (required = false) String companyAddress){
        if (companyName != null && companyAddress != null)
            return deviceService.getDevicesDTOByCompanyNameAndCompanyAddress(companyName, companyAddress);
        else if (companyName != null)
            return deviceService.getDevicesDTOByCompanyName(companyName);
        else if (companyAddress != null)
            return deviceService.getDevicesDTOByCompanyAddress(companyAddress);
        else
            return deviceService.getDevicesDTO();
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addDevice(@RequestBody Device device){
        if(device.getEmployeeOwner() != null && areEmployeeAndCompanyDataValid(device))
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("The employee, who the device is given to, works in a company with different id from the id of the company in which device belongs!");

        if(deviceService.getDeviceDTOBySerialNumber(device.getSerialNumber()) != null)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Device with serial number: " + device.getSerialNumber() + " already exists!");

        return ResponseEntity.status(HttpStatus.CREATED).body(deviceService.saveDeviceDTO(device));
    }

    @PostMapping("/addMany")
    public ResponseEntity<Object> addDevices(@RequestBody List<Device> devices){
        for(Device device : devices) {
            if(device.getEmployeeOwner() != null && areEmployeeAndCompanyDataValid(device))
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("The employee, who the device is given to, works in a company with different id from the id of the company in which device belongs!");
            if (deviceService.getDeviceDTOBySerialNumber(device.getSerialNumber()) != null)
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Device with serial number: " + device.getSerialNumber() + " already exists!");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(deviceService.saveDevicesDTO(devices));
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateDevice(@RequestBody Device device){
        if(device.getEmployeeOwner() != null && areEmployeeAndCompanyDataValid(device))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("The employee, who the device is given to, works in a company with different id from the id of the company in which device belongs!");

        DeviceDTO deviceDTO = deviceService.updateDeviceDTO(device);

        if(deviceDTO == null)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Device with serial number: " + device.getSerialNumber() + " does not exist!");

        return ResponseEntity.status(HttpStatus.OK).body(deviceDTO);
    }

    @PutMapping("/updateMany")
    public ResponseEntity<Object> updateDevices(@RequestBody List<Device> devices){
        for(Device device : devices)
            if(device.getEmployeeOwner() != null && areEmployeeAndCompanyDataValid(device))
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("The employee, who the device is given to, works in a company with different id from the id of the company in which device belongs!");

        List<DeviceDTO> devicesDTO = deviceService.updateDevicesDTO(devices);

        if(devicesDTO == null)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("At least one of the devices does not exist!");

        return ResponseEntity.status(HttpStatus.OK).body(devicesDTO);
    }

    @DeleteMapping("/delete/{serialNumber}")
    public ResponseEntity<Object> deleteDevice(@PathVariable String serialNumber){
        if(deviceService.getDeviceDTOBySerialNumber(serialNumber) == null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Device with serial number: " + serialNumber + " does not exist!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(deviceService.deleteDeviceBySerialNumber(serialNumber));
    }

    private Employee getEmployeeOwnerData(Device device) {
        return employeeRepository.findById(device.getEmployeeOwner().getId()).orElse(null);
    }

    private boolean areEmployeeAndCompanyDataValid(Device device){
        Employee employee = getEmployeeOwnerData(device);
        return device.hasSameCompanyWithDeviceEmployeeOwnerCompany(employee);
    }
}
