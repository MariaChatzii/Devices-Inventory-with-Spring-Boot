package com.inventory.deviceInventory.service;

import com.inventory.deviceInventory.DTO.DeviceDTO;
import com.inventory.deviceInventory.DTO.EmployeeDTO;
import com.inventory.deviceInventory.entity.Company;
import com.inventory.deviceInventory.entity.Device;
import com.inventory.deviceInventory.entity.Employee;
import com.inventory.deviceInventory.mapper.DTOMapper;
import com.inventory.deviceInventory.repository.DeviceRepository;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeviceServiceTest {

    @InjectMocks
    private DeviceService deviceService;
    @Mock
    private DeviceRepository deviceRepository;
    @Mock
    private DTOMapper dtoMapper;
    @Captor
    private ArgumentCaptor<Device> deviceArgumentCaptor;
    @Captor
    private ArgumentCaptor<List<Device>> devicesArgumentCaptor;

    @Test
    public void getDeviceDTOBySerialNumber() {
        List<Device> devices = null;
        List<Employee> employess = null;

        Company company = new Company(5,"mycompany", "anywhere 12, Mexico", employess, devices);
        Employee employee = new Employee(100, "Javie Rojas", "javie@g.com", devices, company);
        Device device = new Device("kk44s","Samsung Galaxy A7 Tab","tablet", employee, company);

        DeviceDTO expectedResponse = new DeviceDTO("Samsung Galaxy A7 Tab", "tablet", "mycompany", "anywhere 12, Mexico", "Javie Rojas", "javie@g.com");

        Mockito.when(deviceRepository.findById("kk44s")).thenReturn(Optional.of(device));
        Mockito.when(dtoMapper.deviceToDeviceDTO(Mockito.any(Device.class))).thenReturn(expectedResponse);

        DeviceDTO actualResponse = deviceService.getDeviceDTOBySerialNumber("kk44s");

        assertEquals(expectedResponse.getName(), actualResponse.getName());
        assertEquals(expectedResponse.getType(), actualResponse.getType());
        assertEquals(expectedResponse.getCompanyOwnerName(), actualResponse.getCompanyOwnerName());
        assertEquals(expectedResponse.getCompanyOwnerAddress(), actualResponse.getCompanyOwnerAddress());
        assertEquals(expectedResponse.getEmployeeOwnerName(), actualResponse.getEmployeeOwnerName());
        assertEquals(expectedResponse.getEmployeeOwnerEmail(), actualResponse.getEmployeeOwnerEmail());
    }

    @Test
    void getDevicesDTO() {
        List<DeviceDTO> expectedResponse = new ArrayList<>();
        DeviceDTO device1 = new DeviceDTO("Samsung S21", "mobile", "mycompany", "anywhere 12, Mexico", "Vasileios Vasileiou", "vasileiou@g.com");
        DeviceDTO device2 = new DeviceDTO("Samsung S20", "mobile", "mycompany2", "anywhere 3, Greece", "John John", "johnjohn@gm.com");

        expectedResponse.add(device1);
        expectedResponse.add(device2);

        Mockito.when(dtoMapper.devicesToDeviceDTOs(deviceRepository.findAll())).thenReturn(expectedResponse);

        List<DeviceDTO> actualResponse = deviceService.getDevicesDTO();

        assertEquals(expectedResponse.size(), actualResponse.size());

    }

    @Test
    void getDevicesDTOByCompanyName() {
        DeviceDTO device1DTO = new DeviceDTO("Samsung Galaxy A7 Tab", "tablet", "mycompany", "anywhere 12, Mexico", "Javie Rojas", "javie@g.com");
        DeviceDTO device2DTO = new DeviceDTO("Samsung Galaxy S21", "mobile", "mycompany", "anywhere 12, Mexico", "John Jackson", "john@g.com");


        List<DeviceDTO> expectedResponse = new ArrayList<>();
        expectedResponse.add(device1DTO);
        expectedResponse.add(device2DTO);

        Mockito.when(dtoMapper.devicesToDeviceDTOs(deviceRepository.findByCompanyOwnerName("mycompany"))).thenReturn(expectedResponse);

        List<DeviceDTO> actualResponse = deviceService.getDevicesDTOByCompanyName("mycompany");

        assertEquals(expectedResponse.size(), actualResponse.size());
        assertEquals(expectedResponse.get(0).getName(), actualResponse.get(0).getName());
    }

    @Test
    void getDevicesDTOByCompanyAddress() {
        DeviceDTO device1DTO = new DeviceDTO("Samsung Galaxy A7 Tab", "tablet", "mycompany", "anywhere 12, Mexico", "Javie Rojas", "javie@g.com");
        DeviceDTO device2DTO = new DeviceDTO("Samsung Galaxy S21", "mobile", "mycompany", "anywhere 12, Mexico", "John Jackson", "john@g.com");

        List<DeviceDTO> expectedResponse = new ArrayList<>();
        expectedResponse.add(device1DTO);
        expectedResponse.add(device2DTO);

        Mockito.when(dtoMapper.devicesToDeviceDTOs(deviceRepository.findByCompanyOwnerName("anywhere 12, Mexico"))).thenReturn(expectedResponse);

        List<DeviceDTO> actualResponse = deviceService.getDevicesDTOByCompanyName("anywhere 12, Mexico");

        assertEquals(expectedResponse.size(), actualResponse.size());
        assertEquals(expectedResponse.get(0).getName(), actualResponse.get(0).getName());
    }

    @Test
    void getDevicesDTOByCompanyNameAndCompanyAddress() {
        DeviceDTO device1DTO = new DeviceDTO("Samsung Galaxy A7 Tab", "tablet", "mycompany", "anywhere 12, Mexico", "Javie Rojas", "javie@g.com");
        DeviceDTO device2DTO = new DeviceDTO("Samsung Galaxy S21", "mobile", "mycompany", "anywhere 12, Mexico", "John Jackson", "john@g.com");

        List<DeviceDTO> expectedResponse = new ArrayList<>();
        expectedResponse.add(device1DTO);
        expectedResponse.add(device2DTO);

        Mockito.when(dtoMapper.devicesToDeviceDTOs(deviceRepository.findByCompanyOwnerNameAndCompanyOwnerAddress("company","anywhere 12, Mexico"))).thenReturn(expectedResponse);

        List<DeviceDTO> actualResponse = deviceService.getDevicesDTOByCompanyNameAndCompanyAddress("company","anywhere 12, Mexico");

        assertEquals(expectedResponse.size(), actualResponse.size());
        assertEquals(expectedResponse.get(0).getName(), actualResponse.get(0).getName());
    }


    @Test
    void getDevicesDTOByNameAndType() {
        DeviceDTO device1DTO = new DeviceDTO("Samsung Galaxy A7 Tab", "tablet", "mycompany", "anywhere 12, Mexico", "Javie Rojas", "javie@g.com");

        List<DeviceDTO> expectedResponse = new ArrayList<>();
        expectedResponse.add(device1DTO);

        Mockito.when(dtoMapper.devicesToDeviceDTOs(deviceRepository.findByNameAndType("Samsung Galaxy A7 Tab","tablet"))).thenReturn(expectedResponse);

        List<DeviceDTO> actualResponse = deviceService.getDevicesDTOByNameAndType("Samsung Galaxy A7 Tab","tablet");

        assertEquals(expectedResponse.size(), actualResponse.size());
        assertEquals(expectedResponse.get(0).getName(), actualResponse.get(0).getName());
    }

    @Test
    void saveDeviceDTO() {
        List<Device> devices = null;
        List<Employee> employess = null;

        Company company = new Company(5,"mycompany", "anywhere 12, Mexico", employess, devices);
        Employee employee = new Employee(100, "Javie Rojas", "javie@g.com", devices, company);
        Device device = new Device("pr1","Samsung Galaxy A7 Tab", "tablet", employee, company);
        DeviceDTO expectedResponse = new DeviceDTO("Samsung Galaxy A7 Tab", "tablet", "mycompany", "anywhere 12, Mexico", "Javie Rojas", "javie@g.com");

        Mockito.when(dtoMapper.deviceToDeviceDTO(deviceRepository.save(device))).thenReturn(expectedResponse);

        deviceService.saveDeviceDTO(device);
        Mockito.verify(deviceRepository, Mockito.times(2)).save(deviceArgumentCaptor.capture());

        Assertions.assertThat(deviceArgumentCaptor.getValue().getSerialNumber()).isEqualTo("pr1");
        Assertions.assertThat(deviceArgumentCaptor.getValue().getName()).isEqualTo("Samsung Galaxy A7 Tab");

    }

    @Test
    void saveDevicesDTO() {
        List<Device> devices = null;
        List<Employee> employess = null;

        Company company = new Company(5,"mycompany", "anywhere 12, Mexico", employess, devices);
        Employee employee = new Employee(100, "Javie Rojas", "javie@g.com", devices, company);

        Device device1 = new Device("pr1","Samsung Galaxy A7 Tab", "tablet", employee, company);
        DeviceDTO device1DTO = new DeviceDTO("Samsung Galaxy A7 Tab", "tablet", "mycompany", "anywhere 12, Mexico", "Javie Rojas", "javie@g.com");
        Device device2 = new Device("pr2","Samsung Galaxy S20", "mobile", employee, company);
        DeviceDTO device2DTO = new DeviceDTO("Samsung Galaxy S20", "mobile", "mycompany", "anywhere 12, Mexico", "Javie Rojas", "javie@g.com");

        List<DeviceDTO> expectedResponse = new ArrayList<>();
        expectedResponse.add(device1DTO);
        expectedResponse.add(device2DTO);

        Mockito.when(dtoMapper.devicesToDeviceDTOs(deviceRepository.saveAll(Stream.of(device1,device2).collect(Collectors.toList())))).thenReturn(expectedResponse);

        deviceService.saveDevicesDTO(Stream.of(device1,device2).collect(Collectors.toList()));
        Mockito.verify(deviceRepository, Mockito.times(2)).saveAll(devicesArgumentCaptor.capture());

        Assertions.assertThat(devicesArgumentCaptor.getValue().get(0).getSerialNumber()).isEqualTo("pr1");
        Assertions.assertThat(devicesArgumentCaptor.getValue().get(0).getName()).isEqualTo("Samsung Galaxy A7 Tab");
        Assertions.assertThat(devicesArgumentCaptor.getValue().get(1).getSerialNumber()).isEqualTo("pr2");
        Assertions.assertThat(devicesArgumentCaptor.getValue().get(1).getName()).isEqualTo("Samsung Galaxy S20");
    }

    @Test
    void updateExistingDeviceDTO() {
        List<Device> devices = null;
        List<Employee> employess = null;

        Company company = new Company(5,"mycompany", "anywhere 12, Mexico", employess, devices);
        Employee employee = new Employee(100, "Javie Rojas", "javie@g.com", devices, company);

        Device existingDevice = new Device("pr1", "Samsung Galaxy S21", "mobile", employee, company);
        Device updateDevice = new Device("pr1", "Samsung Galaxy A7 Tab", "tablet", employee, company);

        DeviceDTO expectedResponse = new DeviceDTO("Samsung Galaxy A7 Tab", "tablet", "mycompany", "anywhere 12, Mexico", "Javie Rojas", "javie@g.com");

        Mockito.when(deviceRepository.findById("pr1")).thenReturn(Optional.of(existingDevice));
        Mockito.when(dtoMapper.deviceToDeviceDTO(deviceRepository.save(updateDevice))).thenReturn(expectedResponse);

        deviceService.updateDeviceDTO(updateDevice);
        Mockito.verify(deviceRepository, Mockito.times(2)).save(deviceArgumentCaptor.capture());

        Assertions.assertThat(deviceArgumentCaptor.getValue().getSerialNumber()).isEqualTo("pr1");
        Assertions.assertThat(deviceArgumentCaptor.getValue().getName()).isEqualTo("Samsung Galaxy A7 Tab");
    }

    @Test
    void updateNotExistingDTO(){
        List<Device> devices = null;
        List<Employee> employess = null;

        Company company = new Company(5,"mycompany", "anywhere 12, Mexico", employess, devices);
        Employee employee = new Employee(100, "Javie Rojas", "javie@g.com", devices, company);

        Device updateDevice = new Device("pr2", "Samsung Galaxy A7 Tab", "tablet", employee, company);

        Mockito.when(deviceRepository.findById("pr2")).thenReturn(null);

        deviceService.updateDeviceDTO(updateDevice);
        Mockito.verify(deviceRepository, Mockito.times(1)).save(deviceArgumentCaptor.capture());

    }

    @Test
    void updateDevicesDTO() {
        List<Device> devices = null;
        List<Employee> employess = null;

        Company company = new Company(5,"mycompany", "anywhere 12, Mexico", employess, devices);
        Employee employee = new Employee(100, "Javie Rojas", "javie@g.com", devices, company);

        Device existingDevice1 = new Device("pr1", "Samsung Galaxy S21", "mobile", employee, company);
        Device existingDevice2 = new Device("pr2", "Iphone 12", "mobile", employee, company);

        Device updateDevice1 = new Device("pr1", "Samsung Galaxy A7 Tab", "tablet", employee, company);
        Device updateDevice2 = new Device("pr2", "Samsung Galaxy A71", "mobile", employee, company);

        DeviceDTO expectedResponse1 = new DeviceDTO("Samsung Galaxy A7 Tab", "tablet", "mycompany", "anywhere 12, Mexico", "Javie Rojas", "javie@g.com");
        DeviceDTO expectedResponse2 = new DeviceDTO("Samsung Galaxy A71", "mobile", "mycompany", "anywhere 12, Mexico", "Javie Rojas", "javie@g.com");

        List<Device> updateDevices = new ArrayList<>();
        updateDevices.add(updateDevice1);
        updateDevices.add(updateDevice2);

        List<DeviceDTO> expectedResponse = new ArrayList<>();
        expectedResponse.add(expectedResponse1);
        expectedResponse.add(expectedResponse2);

        Mockito.when(deviceRepository.findById("pr1")).thenReturn(Optional.of(existingDevice1));
        Mockito.when(deviceRepository.findById("pr2")).thenReturn(Optional.of(existingDevice2));

        Mockito.when(dtoMapper.devicesToDeviceDTOs(deviceRepository.saveAll(updateDevices))).thenReturn(expectedResponse);

        deviceService.updateDevicesDTO(updateDevices);
        Mockito.verify(deviceRepository, Mockito.times(2)).saveAll(devicesArgumentCaptor.capture());

        Assertions.assertThat(devicesArgumentCaptor.getValue().get(0).getSerialNumber()).isEqualTo("pr1");
        Assertions.assertThat(devicesArgumentCaptor.getValue().get(0).getName()).isEqualTo("Samsung Galaxy A7 Tab");
        Assertions.assertThat(devicesArgumentCaptor.getValue().get(1).getSerialNumber()).isEqualTo("pr2");
        Assertions.assertThat(devicesArgumentCaptor.getValue().get(1).getName()).isEqualTo("Samsung Galaxy A71");

    }

    @Test
    void deleteDeviceBySerialNumber() {
        String serialNumber = "pr2s";

        deviceService.deleteDeviceBySerialNumber(serialNumber);

        Mockito.verify(deviceRepository, Mockito.times(1)).deleteById(serialNumber);
    }
}