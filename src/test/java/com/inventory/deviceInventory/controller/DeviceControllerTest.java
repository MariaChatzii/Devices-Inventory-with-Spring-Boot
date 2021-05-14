package com.inventory.deviceInventory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inventory.deviceInventory.DTO.DeviceDTO;
import com.inventory.deviceInventory.entity.Company;
import com.inventory.deviceInventory.entity.Device;
import com.inventory.deviceInventory.entity.Employee;
import com.inventory.deviceInventory.repository.DeviceRepository;
import com.inventory.deviceInventory.service.DeviceService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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


    @Test
    void findAllDevices() throws Exception {
        List<DeviceDTO> expectedResponse = new ArrayList<>();
        DeviceDTO device1 = new DeviceDTO("Samsung S21", "mobile", "mycompany", "anywhere 12, Mexico", "Vasileios Vasileiou", "vasileiou@g.com");
        DeviceDTO device2 = new DeviceDTO("Samsung S20", "mobile", "mycompany2", "anywhere 3, Greece", "John John", "johnjohn@gm.com");

        expectedResponse.add(device1);
        expectedResponse.add(device2);

        Mockito.when(deviceService.getDevicesDTO()).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/device/all")).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is(device1.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].type", Matchers.is(device1.getType())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyOwnerName", Matchers.is(device1.getCompanyOwnerName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyOwnerAddress", Matchers.is(device1.getCompanyOwnerAddress())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employeeOwnerName", Matchers.is(device1.getEmployeeOwnerName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employeeOwnerEmail", Matchers.is(device1.getEmployeeOwnerEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", Matchers.is(device2.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].type", Matchers.is(device2.getType())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].companyOwnerName", Matchers.is(device2.getCompanyOwnerName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].companyOwnerAddress", Matchers.is(device2.getCompanyOwnerAddress())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].employeeOwnerName", Matchers.is(device2.getEmployeeOwnerName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].employeeOwnerEmail", Matchers.is(device2.getEmployeeOwnerEmail())));
    }

    @Test
    void findDeviceDTOBySerialNumber() throws Exception {
        Device device = new Device("pr1", "Samsung S21", "mobile", new Employee(), new Company());
        DeviceDTO expectedResponse = new DeviceDTO("Samsung S21", "mobile", "mycompany", "anywhere 12, Mexico", "Vasileios Vasileiou", "vasileiou@g.com");

        Mockito.when(deviceService.getDeviceDTOBySerialNumber(device.getSerialNumber())).thenReturn(expectedResponse);

        mockMvc.perform(get("/device/pr1")).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(expectedResponse.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyOwnerName", Matchers.is(expectedResponse.getCompanyOwnerName())));
    }

    @Test
    void findDevicesDTOByNameAndType() throws Exception {
        DeviceDTO expectedResponse1 = new DeviceDTO("Samsung S21", "mobile", "mycompany", "anywhere 12, Mexico", "Vasileios Vasileiou", "vasileiou@g.com");
        DeviceDTO expectedResponse2 = new DeviceDTO("Samsung S21", "mobile", "mycompany1", "anywhere 123, Mexico", "Stefanos Stefanou", "stef@g.com");

        Mockito.when(deviceService.getDevicesDTOByNameAndType("Samsung S21", "mobile")).thenReturn(Arrays.asList(expectedResponse1, expectedResponse2));

        mockMvc.perform(get("/device?name=Samsung S21&type=mobile")).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is(expectedResponse1.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].type", Matchers.is(expectedResponse1.getType())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyOwnerName", Matchers.is(expectedResponse1.getCompanyOwnerName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", Matchers.is(expectedResponse2.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].type", Matchers.is(expectedResponse2.getType())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].companyOwnerName", Matchers.is(expectedResponse2.getCompanyOwnerName())));
    }

    @Test
    void findDevicesDTOByCompanyNameAndCompanyAddress() throws Exception {
        DeviceDTO expectedResponse1 = new DeviceDTO("Samsung S21", "mobile", "mycompany", "anywhere 12, Mexico", "Vasileios Vasileiou", "vasileiou@g.com");
        DeviceDTO expectedResponse2 = new DeviceDTO("Samsung S20", "mobile", "mycompany", "anywhere 12, Mexico", "Stefanos Stefanou", "stef@g.com");

        Mockito.when(deviceService.getDevicesDTOByCompanyNameAndCompanyAddress("mycompany", "anywhere 12, Mexico")).thenReturn(Arrays.asList(expectedResponse1, expectedResponse2));

        mockMvc.perform(get("/device/company?companyName=mycompany&companyAddress=anywhere 12, Mexico")).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is(expectedResponse1.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].type", Matchers.is(expectedResponse1.getType())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyOwnerName", Matchers.is(expectedResponse1.getCompanyOwnerName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyOwnerAddress", Matchers.is(expectedResponse1.getCompanyOwnerAddress())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", Matchers.is(expectedResponse2.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].type", Matchers.is(expectedResponse2.getType())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].companyOwnerName", Matchers.is(expectedResponse2.getCompanyOwnerName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].companyOwnerAddress", Matchers.is(expectedResponse2.getCompanyOwnerAddress())));
    }

    @Test
    void findDevicesDTOByCompanyNameAndNotGivenCompanyAddress() throws Exception {
        DeviceDTO expectedResponse1 = new DeviceDTO("Samsung S21", "mobile", "mycompany", "anywhere 12, Mexico", "Vasileios Vasileiou", "vasileiou@g.com");
        DeviceDTO expectedResponse2 = new DeviceDTO("Samsung S20", "mobile", "mycompany", "anywhere 123, Mexico", "Stefanos Stefanou", "stef@g.com");

        Mockito.when(deviceService.getDevicesDTOByCompanyName("mycompany")).thenReturn(Arrays.asList(expectedResponse1, expectedResponse2));

        mockMvc.perform(get("/device/company?companyName=mycompany")).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyOwnerName", Matchers.is(expectedResponse1.getCompanyOwnerName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyOwnerAddress", Matchers.is(expectedResponse1.getCompanyOwnerAddress())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].companyOwnerName", Matchers.is(expectedResponse2.getCompanyOwnerName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].companyOwnerAddress", Matchers.is(expectedResponse2.getCompanyOwnerAddress())));
    }

    @Test
    void findDevicesDTOByCompanyAddressAndNotGivenCompanyName() throws Exception {
        DeviceDTO expectedResponse1 = new DeviceDTO("Samsung S21", "mobile", "mycompany1", "anywhere 12, Mexico", "Vasileios Vasileiou", "vasileiou@g.com");
        DeviceDTO expectedResponse2 = new DeviceDTO("Samsung S20", "mobile", "mycompany", "anywhere 12, Mexico", "Stefanos Stefanou", "stef@g.com");

        Mockito.when(deviceService.getDevicesDTOByCompanyAddress("anywhere 12, Mexico")).thenReturn(Arrays.asList(expectedResponse1, expectedResponse2));

        mockMvc.perform(get("/device/company?companyAddress=anywhere 12, Mexico")).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyOwnerName", Matchers.is(expectedResponse1.getCompanyOwnerName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyOwnerAddress", Matchers.is(expectedResponse1.getCompanyOwnerAddress())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].companyOwnerName", Matchers.is(expectedResponse2.getCompanyOwnerName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].companyOwnerAddress", Matchers.is(expectedResponse2.getCompanyOwnerAddress())));
    }

    @Test
    void findDevicesDTOByNotGivenCompanyNameAndNotGivenCompanyAddress() throws Exception {
        DeviceDTO expectedResponse1 = new DeviceDTO("Samsung S21", "mobile", "mycompany1", "anywhere 12, Mexico", "Vasileios Vasileiou", "vasileiou@g.com");
        DeviceDTO expectedResponse2 = new DeviceDTO("Samsung S20", "mobile", "mycompany", "anywhere 12, Mexico", "Stefanos Stefanou", "stef@g.com");

        Mockito.when(deviceService.getDevicesDTO()).thenReturn(Arrays.asList(expectedResponse1, expectedResponse2));

        mockMvc.perform(get("/device/company?")).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is(expectedResponse1.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", Matchers.is(expectedResponse2.getName())));
    }

    @Test
    void addNotExistingDevice() throws Exception {
        Company company = new Company(5,"mycompany", "anywhere 12, Mexico", new ArrayList<>(), new ArrayList<>());
        Employee employee = new Employee(100, "Javie Rojas", "javie@g.com", new ArrayList<>(), company);
        Device device = new Device("pr2", "Samsung S21", "mobile", employee, company);
        DeviceDTO expectedResponse = new DeviceDTO("Samsung S21", "mobile", "mycompany", "anywhere 12, Mexico", "Vasileios Vasileiou", "vasileiou@g.com");

        Mockito.when(deviceService.saveDeviceDTO(any(Device.class))).thenReturn(expectedResponse);

        mockMvc.perform(post("/device/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(device)))
                .andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(expectedResponse)))
                .andReturn();
    }

    @Test
    void addExistingDevice() throws Exception {
        Company company = new Company(5,"mycompany", "anywhere 12, Mexico", new ArrayList<>(), new ArrayList<>());
        Employee employee = new Employee(100, "Javie Rojas", "javie@g.com", new ArrayList<>(), company);
        Device device = new Device("pr2", "Samsung S21", "mobile", employee, company);
        DeviceDTO expectedResponse = new DeviceDTO(device.getName(), device.getType(), device.getCompanyOwner().getName(), device.getCompanyOwner().getAddress(), device.getEmployeeOwner().getName(), device.getEmployeeOwner().getEmail());

        Mockito.when(deviceService.getDeviceDTOBySerialNumber(device.getSerialNumber())).thenReturn(expectedResponse);

        mockMvc.perform(post("/device/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(device)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Device with serial number: " + device.getSerialNumber() + " already exists"))
                .andReturn();
    }

    @Test
    void addNotExistingDevices() throws Exception {
        Company company1 = new Company(5,"mycompany1", "anywhere 12, Mexico", new ArrayList<>(), new ArrayList<>());
        Employee employee1 = new Employee(100, "Javie Rojas", "javie@g.com", new ArrayList<>(), company1);
        Device device1 = new Device("pr1", "Samsung S21", "mobile", employee1, company1);
        DeviceDTO expectedResponse1 = new DeviceDTO(device1.getName(), device1.getType(), device1.getCompanyOwner().getName(), device1.getCompanyOwner().getAddress(), device1.getEmployeeOwner().getName(), device1.getEmployeeOwner().getEmail());

        Company company2 = new Company(5,"mycompany2", "anywhere 12, Greece", new ArrayList<>(), new ArrayList<>());
        Employee employee2 = new Employee(101, "Alex Rojas", "alex@g.com", new ArrayList<>(), company2);
        Device device2 = new Device("pr2", "Samsung S20", "mobile", employee2, company2);
        DeviceDTO expectedResponse2 = new DeviceDTO(device2.getName(), device2.getType(), device2.getCompanyOwner().getName(), device2.getCompanyOwner().getAddress(), device2.getEmployeeOwner().getName(), device2.getEmployeeOwner().getEmail());

        List<Device> devices = new ArrayList<>();
        devices.add(device1);
        devices.add(device2);

        List<DeviceDTO> expectedResponse = new ArrayList<>();
        expectedResponse.add(expectedResponse1);
        expectedResponse.add(expectedResponse2);

        Mockito.when(deviceService.saveDevicesDTO(any())).thenReturn(expectedResponse);

        mockMvc.perform(post("/device/addMany")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(devices)))
                .andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(expectedResponse)))
                .andReturn();
    }

    @Test
    void addExistingDevices() throws Exception {
        Company company1 = new Company(5,"mycompany1", "anywhere 12, Mexico", new ArrayList<>(), new ArrayList<>());
        Employee employee1 = new Employee(100, "Javie Rojas", "javie@g.com", new ArrayList<>(), company1);
        Device device1 = new Device("pr1", "Samsung S21", "mobile", employee1, company1);

        Device device2 = new Device("pr2", "Samsung S20", "mobile", employee1, company1);
        DeviceDTO expectedResponse2 = new DeviceDTO(device2.getName(), device2.getType(), device2.getCompanyOwner().getName(), device2.getCompanyOwner().getAddress(), device2.getEmployeeOwner().getName(), device2.getEmployeeOwner().getEmail());

        List<Device> devices = new ArrayList<>();
        devices.add(device1);
        devices.add(device2);

        Mockito.when(deviceService.getDeviceDTOBySerialNumber(device2.getSerialNumber())).thenReturn(expectedResponse2);

        mockMvc.perform(post("/device/addMany")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(devices)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Device with serial number: " + device2.getSerialNumber() + " already exists"))
                .andReturn();
    }

    @Test
    void updateExistingDevice() throws Exception {
        Company company = new Company(5,"mycompany", "anywhere 12, Mexico", new ArrayList<>(), new ArrayList<>());
        Employee employee = new Employee(100, "Javie Rojas", "javie@g.com", new ArrayList<>(), company);
        Device device = new Device("pr2", "Samsung S21", "mobile", employee, company);
        DeviceDTO expectedResponse = new DeviceDTO(device.getName(), device.getType(), device.getCompanyOwner().getName(), device.getCompanyOwner().getAddress(), device.getEmployeeOwner().getName(), device.getEmployeeOwner().getEmail());

        Mockito.when(deviceService.updateDeviceDTO(any(Device.class))).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders.put("/device/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(device)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(expectedResponse)))
                .andReturn();
    }

    @Test
    void updateNotExistingDevice() throws Exception {
        Company company = new Company(5, "mycompany", "anywhere 12, Mexico", new ArrayList<>(), new ArrayList<>());
        Employee employee = new Employee(100, "Javie Rojas", "javie@g.com", new ArrayList<>(), company);
        Device device = new Device("pr2", "Samsung S21", "mobile", employee, company);

        mockMvc.perform(MockMvcRequestBuilders.put("/device/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(device)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Device with serial number: " + device.getSerialNumber() + " does not exist"))
                .andReturn();
    }

    @Test
    void updateExistingDevices() throws Exception {
        Company company1 = new Company(5,"mycompany1", "anywhere 12, Mexico", new ArrayList<>(), new ArrayList<>());
        Employee employee1 = new Employee(100, "Javie Rojas", "javie@g.com", new ArrayList<>(), company1);
        Device device1 = new Device("pr1", "Samsung S21", "mobile", employee1, company1);
        DeviceDTO expectedResponse1 = new DeviceDTO(device1.getName(), device1.getType(), device1.getCompanyOwner().getName(), device1.getCompanyOwner().getAddress(), device1.getEmployeeOwner().getName(), device1.getEmployeeOwner().getEmail());

        Company company2 = new Company(5,"mycompany2", "anywhere 12, Greece", new ArrayList<>(), new ArrayList<>());
        Employee employee2 = new Employee(101, "Alex Rojas", "alex@g.com", new ArrayList<>(), company2);
        Device device2 = new Device("pr2", "Samsung S20", "mobile", employee2, company2);
        DeviceDTO expectedResponse2 = new DeviceDTO(device2.getName(), device2.getType(), device2.getCompanyOwner().getName(), device2.getCompanyOwner().getAddress(), device2.getEmployeeOwner().getName(), device2.getEmployeeOwner().getEmail());

        List<Device> devices = new ArrayList<>();
        devices.add(device1);
        devices.add(device2);

        List<DeviceDTO> expectedResponse = new ArrayList<>();
        expectedResponse.add(expectedResponse1);
        expectedResponse.add(expectedResponse2);

        Mockito.when(deviceService.updateDevicesDTO(any())).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders.put("/device/updateMany")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(devices)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(expectedResponse)))
                .andReturn();
    }

    @Test
    void updateNotExistingDevices() throws Exception {
        Company company1 = new Company(5,"mycompany1", "anywhere 12, Mexico", new ArrayList<>(), new ArrayList<>());
        Employee employee1 = new Employee(100, "Javie Rojas", "javie@g.com", new ArrayList<>(), company1);
        Device device1 = new Device("pr1", "Samsung S21", "mobile", employee1, company1);

        Company company2 = new Company(5,"mycompany2", "anywhere 12, Greece", new ArrayList<>(), new ArrayList<>());
        Employee employee2 = new Employee(101, "Alex Rojas", "alex@g.com", new ArrayList<>(), company2);
        Device device2 = new Device("pr2", "Samsung S20", "mobile", employee2, company2);

        List<Device> devices = new ArrayList<>();
        devices.add(device1);
        devices.add(device2);

        Mockito.when(deviceService.updateDevicesDTO(any())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.put("/device/updateMany")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(devices)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("At least one of the devices does not exist"))
                .andReturn();
    }

    @Test
    void deleteExistingDevice() throws Exception {
        Company company = new Company(5,"mycompany1", "anywhere 12, Mexico", new ArrayList<>(), new ArrayList<>());
        Employee employee = new Employee(100, "Javie Rojas", "javie@g.com", new ArrayList<>(), company);
        Device device = new Device("pr1", "Samsung S21", "mobile", employee, company);
        DeviceDTO deviceDTO = new DeviceDTO(device.getName(), device.getType(), device.getCompanyOwner().getName(), device.getCompanyOwner().getAddress(), device.getEmployeeOwner().getName(), device.getEmployeeOwner().getEmail());
        String expectedResponse = "Device with serial number: " + device.getSerialNumber() + " is successfully removed!";

        Mockito.when(deviceService.getDeviceDTOBySerialNumber(any())).thenReturn(deviceDTO);
        Mockito.when(deviceService.deleteDeviceBySerialNumber(any())).thenReturn(expectedResponse);

        mockMvc.perform(delete("/device/delete/" + device.getSerialNumber()))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse))
                .andReturn();
    }

    @Test
    void deleteNotExistingDevice() throws Exception {
        Company company = new Company(5,"mycompany1", "anywhere 12, Mexico", new ArrayList<>(), new ArrayList<>());
        Employee employee = new Employee(100, "Javie Rojas", "javie@g.com", new ArrayList<>(), company);
        Device device = new Device("pr1", "Samsung S21", "mobile", employee, company);

        mockMvc.perform(delete("/device/delete/" + device.getSerialNumber()))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Device with serial number: " + device.getSerialNumber() + " does not exist"))
                .andReturn();
    }
}