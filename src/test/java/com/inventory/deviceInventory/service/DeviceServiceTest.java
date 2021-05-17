package com.inventory.deviceInventory.service;

import com.inventory.deviceInventory.DTO.DeviceDTO;
import com.inventory.deviceInventory.entity.Company;
import com.inventory.deviceInventory.entity.Device;
import com.inventory.deviceInventory.entity.Employee;
import com.inventory.deviceInventory.mapper.DTOMapper;
import com.inventory.deviceInventory.repository.DeviceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class DeviceServiceTest {

    @InjectMocks
    private DeviceService deviceService;
    @Mock
    private DeviceRepository deviceRepository;
    @Mock
    private DTOMapper dtoMapper;

    Device device1, device2, device3, device4;
    DeviceDTO deviceDTO1, deviceDTO2, deviceDTO3, deviceDTO4;
    List<DeviceDTO> deviceDTOs, sameNameAndTypeDeviceDTOs, sameCompanyDeviceDTOs,
            sameCompanyNameAndDiffCompanyAddressDeviceDTOs, sameCompanyAddressAndDiffCompanyNameDeviceDTOs;
    List<Device> devices;


    @BeforeEach
    void setup() {
        Company company1 = new Company(100, "mycompany1", "anywhere 12, Mexico", new ArrayList<>(), new ArrayList<>());
        Company company2 = new Company(101, "mycompany1", "anywhere 12, Greece", new ArrayList<>(), new ArrayList<>());
        Company company3 = new Company(102, "mycompany2", "anywhere 12, Greece", new ArrayList<>(), new ArrayList<>());

        Employee employee1 = new Employee(1, "John John", "john@gm.com", new ArrayList<>(), company1);
        Employee employee2 = new Employee(2, "Maria Jackson", "mar@gm.com", new ArrayList<>(), company2);

        device1 = new Device("pr1", "Samsung S21", "mobile", employee1, company1);
        device2 = new Device("pr2", "Samsung S20", "mobile", employee2, company2);
        device3 = new Device("pr3", "Samsung S21", "mobile", employee2, company2);
        device4 = new Device("pr4", "Iphone 11", "mobile", new Employee(), company3);

        deviceDTO1 = new DeviceDTO(device1.getName(), device1.getType(), device1.getCompanyOwner().getName(), device1.getCompanyOwner().getAddress(), device1.getEmployeeOwner().getName(), device1.getEmployeeOwner().getEmail());
        deviceDTO2 = new DeviceDTO(device2.getName(), device2.getType(), device1.getCompanyOwner().getName(), device2.getCompanyOwner().getAddress(), device2.getEmployeeOwner().getName(), device2.getEmployeeOwner().getEmail());
        deviceDTO3 = new DeviceDTO(device3.getName(), device3.getType(), device3.getCompanyOwner().getName(), device3.getCompanyOwner().getAddress(), device3.getEmployeeOwner().getName(), device3.getEmployeeOwner().getEmail());
        deviceDTO4 = new DeviceDTO(device4.getName(), device4.getType(), device4.getCompanyOwner().getName(), device4.getCompanyOwner().getAddress(), device4.getEmployeeOwner().getName(), device4.getEmployeeOwner().getEmail());

        devices = Stream.of(device1, device2).collect(Collectors.toList());
        deviceDTOs = Stream.of(deviceDTO1, deviceDTO2).collect(Collectors.toList());

        sameNameAndTypeDeviceDTOs = Stream.of(deviceDTO1, deviceDTO3).collect(Collectors.toList());
        sameCompanyDeviceDTOs = Stream.of(deviceDTO1, deviceDTO3).collect(Collectors.toList());
        sameCompanyNameAndDiffCompanyAddressDeviceDTOs = Stream.of(deviceDTO1, deviceDTO2).collect(Collectors.toList());
        sameCompanyAddressAndDiffCompanyNameDeviceDTOs = Stream.of( deviceDTO3, deviceDTO4).collect(Collectors.toList());
    }

    @Test
    public void getDeviceDTOBySerialNumber() {
        Mockito.when(deviceRepository.findById(device1.getSerialNumber())).thenReturn(Optional.of(device1));
        Mockito.when(dtoMapper.deviceToDeviceDTO(Mockito.any(Device.class))).thenReturn(deviceDTO1);

        DeviceDTO actualResponse = deviceService.getDeviceDTOBySerialNumber(device1.getSerialNumber());

        assertEquals(deviceDTO1.getName(), actualResponse.getName());
        assertEquals(deviceDTO1.getType(), actualResponse.getType());
        assertEquals(deviceDTO1.getCompanyOwnerName(), actualResponse.getCompanyOwnerName());
        assertEquals(deviceDTO1.getCompanyOwnerAddress(), actualResponse.getCompanyOwnerAddress());
        assertEquals(deviceDTO1.getEmployeeOwnerName(), actualResponse.getEmployeeOwnerName());
        assertEquals(deviceDTO1.getEmployeeOwnerEmail(), actualResponse.getEmployeeOwnerEmail());
    }

    @Test
    void getDevicesDTO() {
        Mockito.when(dtoMapper.devicesToDeviceDTOs(deviceRepository.findAll())).thenReturn(deviceDTOs);

        List<DeviceDTO> actualResponse = deviceService.getDevicesDTO();

        assertEquals(deviceDTOs.size(), actualResponse.size());

    }

    @Test
    void getDevicesDTOByCompanyName() {
        Mockito.when(dtoMapper.devicesToDeviceDTOs(deviceRepository.findByCompanyOwnerName(sameCompanyNameAndDiffCompanyAddressDeviceDTOs.get(0).getCompanyOwnerName())))
                .thenReturn(sameCompanyNameAndDiffCompanyAddressDeviceDTOs);

        List<DeviceDTO> actualResponse = deviceService.getDevicesDTOByCompanyName(sameCompanyNameAndDiffCompanyAddressDeviceDTOs.get(0).getCompanyOwnerName());

        assertEquals(sameCompanyNameAndDiffCompanyAddressDeviceDTOs.size(), actualResponse.size());
        assertEquals(sameCompanyNameAndDiffCompanyAddressDeviceDTOs.get(0).getName(), actualResponse.get(0).getName());
        assertEquals(sameCompanyNameAndDiffCompanyAddressDeviceDTOs.get(1).getName(), actualResponse.get(1).getName());
    }

    @Test
    void getDevicesDTOByCompanyAddress() {
        Mockito.when(dtoMapper.devicesToDeviceDTOs(deviceRepository.findByCompanyOwnerAddress(sameCompanyAddressAndDiffCompanyNameDeviceDTOs.get(0).getCompanyOwnerAddress())))
                .thenReturn(sameCompanyAddressAndDiffCompanyNameDeviceDTOs);

        List<DeviceDTO> actualResponse = deviceService.getDevicesDTOByCompanyAddress(sameCompanyAddressAndDiffCompanyNameDeviceDTOs.get(0).getCompanyOwnerAddress());

        assertEquals(sameCompanyAddressAndDiffCompanyNameDeviceDTOs.size(), actualResponse.size());
        assertEquals(sameCompanyAddressAndDiffCompanyNameDeviceDTOs.get(0).getName(), actualResponse.get(0).getName());
        assertEquals(sameCompanyAddressAndDiffCompanyNameDeviceDTOs.get(1).getName(), actualResponse.get(1).getName());
    }

    @Test
    void getDevicesDTOByCompanyNameAndCompanyAddress() {
        Mockito.when(dtoMapper.devicesToDeviceDTOs(deviceRepository.findByCompanyOwnerNameAndCompanyOwnerAddress(sameCompanyDeviceDTOs.get(0).getCompanyOwnerName(), sameCompanyDeviceDTOs.get(0).getCompanyOwnerAddress())))
                .thenReturn(sameCompanyDeviceDTOs);

        List<DeviceDTO> actualResponse = deviceService.getDevicesDTOByCompanyNameAndCompanyAddress(sameCompanyDeviceDTOs.get(0).getCompanyOwnerName(), sameCompanyDeviceDTOs.get(0).getCompanyOwnerAddress());

        assertEquals(sameCompanyDeviceDTOs.size(), actualResponse.size());
        assertEquals(sameCompanyDeviceDTOs.get(0).getName(), actualResponse.get(0).getName());
        assertEquals(sameCompanyDeviceDTOs.get(1).getName(), actualResponse.get(1).getName());
    }

    @Test
    void getDevicesDTOByNameAndType() {
        Mockito.when(dtoMapper.devicesToDeviceDTOs(deviceRepository.findByNameAndType(sameNameAndTypeDeviceDTOs.get(0).getName(), sameNameAndTypeDeviceDTOs.get(0).getType())))
                .thenReturn(sameNameAndTypeDeviceDTOs);

        List<DeviceDTO> actualResponse = deviceService.getDevicesDTOByNameAndType(sameNameAndTypeDeviceDTOs.get(0).getName(), sameNameAndTypeDeviceDTOs.get(0).getType());

        assertEquals(sameNameAndTypeDeviceDTOs.size(), actualResponse.size());
        assertEquals(sameNameAndTypeDeviceDTOs.get(0).getName(), actualResponse.get(0).getName());
        assertEquals(sameNameAndTypeDeviceDTOs.get(1).getName(), actualResponse.get(1).getName());
    }

    @Test
    void saveDeviceDTO() {
        Mockito.when(dtoMapper.deviceToDeviceDTO(deviceRepository.save(device1))).thenReturn(deviceDTO1);

        DeviceDTO actualResponse = deviceService.saveDeviceDTO(device1);
        Mockito.verify(deviceRepository, Mockito.times(2)).save(device1);

        assertEquals(deviceDTO1.getName(), actualResponse.getName());
        assertEquals(deviceDTO1.getType(), actualResponse.getType());
    }

    @Test
    void saveDevicesDTO() {
        Mockito.when(dtoMapper.devicesToDeviceDTOs(deviceRepository.saveAll(devices))).thenReturn(deviceDTOs);

        List<DeviceDTO> actualResponse = deviceService.saveDevicesDTO(devices);
        Mockito.verify(deviceRepository, Mockito.times(2)).saveAll(devices);

        assertEquals(deviceDTOs.get(0).getName(), actualResponse.get(0).getName());
        assertEquals(deviceDTOs.get(0).getType(), actualResponse.get(0).getType());
        assertEquals(deviceDTOs.get(1).getName(), actualResponse.get(1).getName());
        assertEquals(deviceDTOs.get(1).getType(), actualResponse.get(1).getType());
    }

    @Test
    void updateExistingDeviceDTO() {
        Mockito.when(deviceRepository.findById(device1.getSerialNumber())).thenReturn(Optional.of(device1));
        Mockito.when(dtoMapper.deviceToDeviceDTO(deviceRepository.save(device1))).thenReturn(deviceDTO1);

        DeviceDTO actualResponse = deviceService.updateDeviceDTO(device1);
        Mockito.verify(deviceRepository, Mockito.times(2)).save(device1);

        assertEquals(deviceDTO1.getName(), actualResponse.getName());
        assertEquals(deviceDTO1.getType(), actualResponse.getType());
    }

    @Test
    void updateNotExistingDTO(){
        DeviceDTO actualResponse = deviceService.updateDeviceDTO(device1);
        assertNull(actualResponse);
        Mockito.verify(deviceRepository, Mockito.times(0)).save(Mockito.any());
    }

    @Test
    void updateExistingDevicesDTO() {
        Mockito.when(deviceRepository.findById(devices.get(0).getSerialNumber())).thenReturn(Optional.of(devices.get(0)));
        Mockito.when(deviceRepository.findById(devices.get(1).getSerialNumber())).thenReturn(Optional.of(devices.get(1)));

        Mockito.when(dtoMapper.devicesToDeviceDTOs(deviceRepository.saveAll(devices))).thenReturn(deviceDTOs);

        List<DeviceDTO> actualResponse = deviceService.updateDevicesDTO(devices);
        Mockito.verify(deviceRepository, Mockito.times(2)).saveAll(devices);

        assertEquals(deviceDTOs.size(), actualResponse.size());
        assertEquals(deviceDTOs.get(0).getName(), actualResponse.get(0).getName());
        assertEquals(deviceDTOs.get(1).getName(), actualResponse.get(1).getName());
    }

    @Test
    void updateNotExistingDevicesDTO() {
        Mockito.when(deviceRepository.findById(device1.getSerialNumber())).thenReturn(Optional.of(device1));

        List<DeviceDTO> actualResponse = deviceService.updateDevicesDTO(devices);
        assertNull(actualResponse);
        Mockito.verify(deviceRepository, Mockito.times(0)).saveAll(Mockito.any());
    }

    @Test
    void deleteDeviceBySerialNumber() {
        String actualResponse = deviceService.deleteDeviceBySerialNumber(device1.getSerialNumber());
        Mockito.verify(deviceRepository, Mockito.times(1)).deleteById(device1.getSerialNumber());

        assertEquals("Device with serial number: " + device1.getSerialNumber() + " is successfully removed!", actualResponse);
    }
}