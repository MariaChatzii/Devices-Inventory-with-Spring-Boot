package com.inventory.deviceInventory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inventory.deviceInventory.DTO.DeviceDTO;
import com.inventory.deviceInventory.DTO.EmployeeDTO;
import com.inventory.deviceInventory.entity.Company;
import com.inventory.deviceInventory.entity.Device;
import com.inventory.deviceInventory.entity.Employee;
import com.inventory.deviceInventory.repository.DeviceRepository;
import com.inventory.deviceInventory.service.DeviceService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = DeviceController.class)
class DeviceControllerTest {

    ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    DeviceService deviceService;
    @MockBean
    DeviceRepository deviceRepository;
    @Autowired
    MockMvc mockMvc;

    Device device1, device2, device3, device4;
    DeviceDTO deviceDTO1, deviceDTO2, deviceDTO3, deviceDTO4;
    List<DeviceDTO> deviceDTOs, sameNameAndTypeDeviceDTOs, sameCompanyDeviceDTOs,
            sameCompanyNameAndDiffCompanyAddressDeviceDTOs, sameCompanyAddressAndDiffCompanyNameDeviceDTOs;
    List<Device> devices;

    @BeforeEach
    void setup(){
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
    void findAllDevices() throws Exception {

        Mockito.when(deviceService.getDevicesDTO()).thenReturn(deviceDTOs);

        mockMvc.perform(MockMvcRequestBuilders.get("/device/all")).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is(deviceDTOs.get(0).getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].type", Matchers.is(deviceDTOs.get(0).getType())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyOwnerName", Matchers.is(deviceDTOs.get(0).getCompanyOwnerName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyOwnerAddress", Matchers.is(deviceDTOs.get(0).getCompanyOwnerAddress())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employeeOwnerName", Matchers.is(deviceDTOs.get(0).getEmployeeOwnerName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employeeOwnerEmail", Matchers.is(deviceDTOs.get(0).getEmployeeOwnerEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", Matchers.is(deviceDTOs.get(1).getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].type", Matchers.is(deviceDTOs.get(1).getType())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].companyOwnerName", Matchers.is(deviceDTOs.get(1).getCompanyOwnerName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].companyOwnerAddress", Matchers.is(deviceDTOs.get(1).getCompanyOwnerAddress())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].employeeOwnerName", Matchers.is(deviceDTOs.get(1).getEmployeeOwnerName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].employeeOwnerEmail", Matchers.is(deviceDTOs.get(1).getEmployeeOwnerEmail())));
    }

    @Test
    void findDeviceDTOBySerialNumber() throws Exception {

        Mockito.when(deviceService.getDeviceDTOBySerialNumber(device1.getSerialNumber())).thenReturn(deviceDTO1);

        mockMvc.perform(get("/device/" + device1.getSerialNumber())).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(deviceDTO1.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyOwnerName", Matchers.is(deviceDTO1.getCompanyOwnerName())));
    }

    @Test
    void findDevicesDTOByNameAndType() throws Exception {
        Mockito.when(deviceService.getDevicesDTOByNameAndType(sameNameAndTypeDeviceDTOs.get(0).getName(), sameNameAndTypeDeviceDTOs.get(0).getType()))
                .thenReturn(sameNameAndTypeDeviceDTOs);

        mockMvc.perform(get("/device?name=" + sameNameAndTypeDeviceDTOs.get(0).getName() +"&type=" + sameNameAndTypeDeviceDTOs.get(0).getType()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].name", Matchers.is(sameNameAndTypeDeviceDTOs.get(0).getName())))
                .andExpect(jsonPath("$[0].type", Matchers.is(sameNameAndTypeDeviceDTOs.get(0).getType())))
                .andExpect(jsonPath("$[0].companyOwnerName", Matchers.is(sameNameAndTypeDeviceDTOs.get(0).getCompanyOwnerName())))
                .andExpect(jsonPath("$[1].name", Matchers.is(sameNameAndTypeDeviceDTOs.get(1).getName())))
                .andExpect(jsonPath("$[1].type", Matchers.is(sameNameAndTypeDeviceDTOs.get(1).getType())))
                .andExpect(jsonPath("$[1].companyOwnerName", Matchers.is(sameNameAndTypeDeviceDTOs.get(1).getCompanyOwnerName())));
    }

    @Test
    void findDevicesDTOByCompanyNameAndCompanyAddress() throws Exception {
        Mockito.when(deviceService.getDevicesDTOByCompanyNameAndCompanyAddress(sameCompanyDeviceDTOs.get(0).getCompanyOwnerName(), sameCompanyDeviceDTOs.get(0).getCompanyOwnerAddress()))
                .thenReturn(sameCompanyDeviceDTOs);

        mockMvc.perform(get("/device/company?companyName=" + sameCompanyDeviceDTOs.get(0).getCompanyOwnerName() + "&companyAddress=" + sameCompanyDeviceDTOs.get(0).getCompanyOwnerAddress()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].name", Matchers.is(sameCompanyDeviceDTOs.get(0).getName())))
                .andExpect(jsonPath("$[0].type", Matchers.is(sameCompanyDeviceDTOs.get(0).getType())))
                .andExpect(jsonPath("$[0].companyOwnerName", Matchers.is(sameCompanyDeviceDTOs.get(0).getCompanyOwnerName())))
                .andExpect(jsonPath("$[0].companyOwnerAddress", Matchers.is(sameCompanyDeviceDTOs.get(0).getCompanyOwnerAddress())))
                .andExpect(jsonPath("$[1].name", Matchers.is(sameCompanyDeviceDTOs.get(1).getName())))
                .andExpect(jsonPath("$[1].type", Matchers.is(sameCompanyDeviceDTOs.get(1).getType())))
                .andExpect(jsonPath("$[1].companyOwnerName", Matchers.is(sameCompanyDeviceDTOs.get(1).getCompanyOwnerName())))
                .andExpect(jsonPath("$[1].companyOwnerAddress", Matchers.is(sameCompanyDeviceDTOs.get(1).getCompanyOwnerAddress())));
    }

    @Test
    void findDevicesDTOByCompanyNameAndNotGivenCompanyAddress() throws Exception {
        Mockito.when(deviceService.getDevicesDTOByCompanyName(sameCompanyNameAndDiffCompanyAddressDeviceDTOs.get(0).getCompanyOwnerName())).thenReturn(sameCompanyNameAndDiffCompanyAddressDeviceDTOs);

        mockMvc.perform(get("/device/company?companyName=" + sameCompanyNameAndDiffCompanyAddressDeviceDTOs.get(0).getCompanyOwnerName()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyOwnerName", Matchers.is(sameCompanyNameAndDiffCompanyAddressDeviceDTOs.get(0).getCompanyOwnerName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyOwnerAddress", Matchers.is(sameCompanyNameAndDiffCompanyAddressDeviceDTOs.get(0).getCompanyOwnerAddress())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].companyOwnerName", Matchers.is(sameCompanyNameAndDiffCompanyAddressDeviceDTOs.get(1).getCompanyOwnerName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].companyOwnerAddress", Matchers.is(sameCompanyNameAndDiffCompanyAddressDeviceDTOs.get(1).getCompanyOwnerAddress())));
    }

    @Test
    void findDevicesDTOByCompanyAddressAndNotGivenCompanyName() throws Exception {
        Mockito.when(deviceService.getDevicesDTOByCompanyAddress(sameCompanyAddressAndDiffCompanyNameDeviceDTOs.get(0).getCompanyOwnerAddress()))
                .thenReturn(sameCompanyAddressAndDiffCompanyNameDeviceDTOs);

        mockMvc.perform(get("/device/company?companyAddress=" + sameCompanyAddressAndDiffCompanyNameDeviceDTOs.get(0).getCompanyOwnerAddress())).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyOwnerName", Matchers.is(sameCompanyAddressAndDiffCompanyNameDeviceDTOs.get(0).getCompanyOwnerName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyOwnerAddress", Matchers.is(sameCompanyAddressAndDiffCompanyNameDeviceDTOs.get(0).getCompanyOwnerAddress())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].companyOwnerName", Matchers.is(sameCompanyAddressAndDiffCompanyNameDeviceDTOs.get(1).getCompanyOwnerName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].companyOwnerAddress", Matchers.is(sameCompanyAddressAndDiffCompanyNameDeviceDTOs.get(1).getCompanyOwnerAddress())));
    }

    @Test
    void findDevicesDTOByNotGivenCompanyNameAndNotGivenCompanyAddress() throws Exception {
        Mockito.when(deviceService.getDevicesDTO()).thenReturn(deviceDTOs);

        mockMvc.perform(get("/device/company?")).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is(deviceDTOs.get(0).getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", Matchers.is(deviceDTOs.get(1).getName())));
    }

    @Test
    void addNotExistingDevice() throws Exception {
        Mockito.when(deviceService.saveDeviceDTO(any(Device.class))).thenReturn(deviceDTO1);

        mockMvc.perform(post("/device/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(device1)))
                .andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(deviceDTO1)))
                .andReturn();
    }

    @Test
    void addExistingDevice() throws Exception {
        Mockito.when(deviceService.getDeviceDTOBySerialNumber(device1.getSerialNumber())).thenReturn(deviceDTO1);

        mockMvc.perform(post("/device/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(device1)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Device with serial number: " + device1.getSerialNumber() + " already exists!"))
                .andReturn();
    }

    @Test
    void addNotExistingDevices() throws Exception {
        Mockito.when(deviceService.saveDevicesDTO(any())).thenReturn(deviceDTOs);

        mockMvc.perform(post("/device/addMany")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(devices)))
                .andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(deviceDTOs)))
                .andReturn();
    }

    @Test
    void addExistingDevices() throws Exception {
        Mockito.when(deviceService.getDeviceDTOBySerialNumber(devices.get(1).getSerialNumber())).thenReturn(deviceDTOs.get(1));

        mockMvc.perform(post("/device/addMany")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(devices)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Device with serial number: " + devices.get(1).getSerialNumber() + " already exists!"))
                .andReturn();
    }

    @Test
    void updateExistingDevice() throws Exception {
        Mockito.when(deviceService.updateDeviceDTO(any(Device.class))).thenReturn(deviceDTO1);

        mockMvc.perform(MockMvcRequestBuilders.put("/device/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(device1)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(deviceDTO1)))
                .andReturn();
    }

    @Test
    void updateNotExistingDevice() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/device/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(device1)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Device with serial number: " + device1.getSerialNumber() + " does not exist!"))
                .andReturn();
    }

    @Test
    void updateExistingDevices() throws Exception {
        Mockito.when(deviceService.updateDevicesDTO(any())).thenReturn(deviceDTOs);

        mockMvc.perform(MockMvcRequestBuilders.put("/device/updateMany")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(devices)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(deviceDTOs)))
                .andReturn();
    }

    @Test
    void updateNotExistingDevices() throws Exception {
        Mockito.when(deviceService.updateDevicesDTO(any())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.put("/device/updateMany")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(devices)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("At least one of the devices does not exist!"))
                .andReturn();
    }

    @Test
    void deleteExistingDevice() throws Exception {
        String expectedResponse = "Device with serial number: " + device1.getSerialNumber() + " is successfully removed";

        Mockito.when(deviceService.getDeviceDTOBySerialNumber(any())).thenReturn(deviceDTO1);
        Mockito.when(deviceService.deleteDeviceBySerialNumber(any())).thenReturn(expectedResponse);

        mockMvc.perform(delete("/device/delete/" + device1.getSerialNumber()))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse))
                .andReturn();
    }

    @Test
    void deleteNotExistingDevice() throws Exception {
        mockMvc.perform(delete("/device/delete/" + device1.getSerialNumber()))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Device with serial number: " + device1.getSerialNumber() + " does not exist!"))
                .andReturn();
    }
}