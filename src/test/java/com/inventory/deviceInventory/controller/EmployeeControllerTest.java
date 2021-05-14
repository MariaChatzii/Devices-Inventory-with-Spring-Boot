package com.inventory.deviceInventory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.inventory.deviceInventory.DTO.EmployeeDTO;
import com.inventory.deviceInventory.entity.Company;
import com.inventory.deviceInventory.entity.Employee;
import com.inventory.deviceInventory.repository.EmployeeRepository;
import com.inventory.deviceInventory.service.EmployeeService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = EmployeeController.class)
class EmployeeControllerTest {

    ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    EmployeeService employeeService;
    @MockBean
    EmployeeRepository employeeRepository;
    @Autowired
    MockMvc mockMvc;

    private Employee employee1;
    private EmployeeDTO employeeDTO1;
    private List<Employee> employees, sameNameEmployees, sameCompanyEmployees, sameCompanyAddressAndDiffCompanyName;
    private List<EmployeeDTO> employeeDTOs, sameNameEmployeeDTOs, sameCompanyEmployeeDTOs, sameCompanyAddressAndDiffCompanyNameDTOs;

    @BeforeEach
    void setup(){
        Company company1 = new Company(100, "mycompany1", "anywhere 12, Mexico", new ArrayList<>(), new ArrayList<>());
        Company company2 = new Company(101, "mycompany1", "anywhere 12, Greece", new ArrayList<>(), new ArrayList<>());
        Company company3 = new Company(102, "mycompany2", "anywhere 12, Greece", new ArrayList<>(), new ArrayList<>());

        employee1 = new Employee(1, "John John", "john@gm.com", new ArrayList<>(), company1);
        Employee employee2 = new Employee(2, "Maria Jackson", "mar@gm.com", new ArrayList<>(), company2);
        Employee employee3 = new Employee(3, "John John", "j@gm.com", new ArrayList<>(), company1);
        Employee employee4 = new Employee(4, "Maria Rojas", "rojas@gm.com", new ArrayList<>(), company3);

        employeeDTO1 = new EmployeeDTO(employee1.getName(), employee1.getEmail(), new ArrayList<>(), employee1.getCompany().getName(), employee1.getCompany().getAddress(), employee1.getDevices().size());
        EmployeeDTO employeeDTO2 = new EmployeeDTO(employee2.getName(), employee2.getEmail(), new ArrayList<>(), employee2.getCompany().getName(), employee2.getCompany().getAddress(), employee2.getDevices().size());
        EmployeeDTO employeeDTO3 = new EmployeeDTO(employee3.getName(), employee3.getEmail(), new ArrayList<>(), employee3.getCompany().getName(), employee3.getCompany().getAddress(), employee3.getDevices().size());
        EmployeeDTO employeeDTO4 = new EmployeeDTO(employee4.getName(), employee4.getEmail(), new ArrayList<>(), employee4.getCompany().getName(), employee4.getCompany().getAddress(), employee4.getDevices().size());

        employees = Stream.of(employee1, employee2).collect(Collectors.toList());
        employeeDTOs = Stream.of(employeeDTO1, employeeDTO2).collect(Collectors.toList());

        sameNameEmployees = Stream.of(employee1, employee3).collect(Collectors.toList());
        sameNameEmployeeDTOs = Stream.of(employeeDTO1, employeeDTO3).collect(Collectors.toList());

        sameCompanyEmployees = Stream.of(employee1, employee3).collect(Collectors.toList());
        sameCompanyEmployeeDTOs = Stream.of(employeeDTO1, employeeDTO3).collect(Collectors.toList());

        sameCompanyAddressAndDiffCompanyName = (Stream.of(employee2, employee4).collect(Collectors.toList()));
        sameCompanyAddressAndDiffCompanyNameDTOs = (Stream.of(employeeDTO2, employeeDTO4).collect(Collectors.toList()));

    }

    @Test
    void findAllEmployees() throws Exception {
        Mockito.when(employeeService.getEmployeesDTO()).thenReturn(employeeDTOs);

        mockMvc.perform(MockMvcRequestBuilders.get("/employee/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is(employeeDTOs.get(0).getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email", Matchers.is(employeeDTOs.get(0).getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyAddress", Matchers.is(employeeDTOs.get(0).getCompanyAddress())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].devicesCount", Matchers.is(employeeDTOs.get(0).getDevicesCount())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", Matchers.is(employeeDTOs.get(1).getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].email", Matchers.is(employeeDTOs.get(1).getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].companyAddress", Matchers.is(employeeDTOs.get(1).getCompanyAddress())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].devicesCount", Matchers.is(employeeDTOs.get(1).getDevicesCount())));
    }

    @Test
    void findEmployeeById() throws Exception {
        Mockito.when(employeeService.getEmployeeById(employee1.getId())).thenReturn(employeeDTO1);

        mockMvc.perform(MockMvcRequestBuilders.get("/employee/" + employee1.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(employeeDTO1.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is(employeeDTO1.getEmail())));
    }

    @Test
    void findEmployeeDTOByEmail() throws Exception {
        Mockito.when(employeeService.getEmployeeDTOByEmail(employee1.getEmail())).thenReturn(employeeDTO1);

        mockMvc.perform(MockMvcRequestBuilders.get("/employee/email/" + employee1.getEmail()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(employeeDTO1.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is(employeeDTO1.getEmail())));
    }

    @Test
    void findEmployeesDTOByName() throws Exception {
        Mockito.when(employeeService.getEmployeesDTOByName(sameNameEmployees.get(0).getName())).thenReturn(sameNameEmployeeDTOs);

        mockMvc.perform(MockMvcRequestBuilders.get("/employee/name/" + sameNameEmployees.get(0).getName()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is(sameNameEmployeeDTOs.get(0).getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email", Matchers.is(sameNameEmployeeDTOs.get(0).getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyAddress", Matchers.is(sameNameEmployeeDTOs.get(0).getCompanyAddress())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].devicesCount", Matchers.is(sameNameEmployeeDTOs.get(0).getDevicesCount())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", Matchers.is(sameNameEmployeeDTOs.get(1).getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].email", Matchers.is(sameNameEmployeeDTOs.get(1).getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].companyAddress", Matchers.is(sameNameEmployeeDTOs.get(1).getCompanyAddress())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].devicesCount", Matchers.is(sameNameEmployeeDTOs.get(1).getDevicesCount())));
    }

    @Test
    void findEmployeesDTOByCompanyNameAndCompanyAddress() throws Exception {
        Mockito.when(employeeService.getEmployeesDTOByCompanyNameAndCompanyAddress(sameCompanyEmployees.get(0).getCompany().getName(), sameCompanyEmployees.get(0).getCompany().getAddress()))
                .thenReturn(sameCompanyEmployeeDTOs);

        mockMvc.perform(MockMvcRequestBuilders.get("/employee/company?companyName=" + sameCompanyEmployees.get(0).getCompany().getName() + "&companyAddress=" + sameCompanyEmployees.get(0).getCompany().getAddress()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email", Matchers.is(sameCompanyEmployeeDTOs.get(0).getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyName", Matchers.is(sameCompanyEmployeeDTOs.get(0).getCompanyName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyAddress", Matchers.is(sameCompanyEmployeeDTOs.get(0).getCompanyAddress())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].email", Matchers.is(sameCompanyEmployeeDTOs.get(1).getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].companyName", Matchers.is(sameCompanyEmployeeDTOs.get(1).getCompanyName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].companyAddress", Matchers.is(sameCompanyEmployeeDTOs.get(1).getCompanyAddress())));
    }

    @Test
    void findEmployeesDTOByCompanyNameAndNotGivenCompanyAddress() throws Exception {
        Mockito.when(employeeService.getEmployeesDTOByCompanyName(employees.get(0).getCompany().getName())).thenReturn(employeeDTOs);

        mockMvc.perform(MockMvcRequestBuilders.get("/employee/company?companyName=" + employees.get(0).getCompany().getName()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email", Matchers.is(employeeDTOs.get(0).getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyName", Matchers.is(employeeDTOs.get(0).getCompanyName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyAddress", Matchers.is(employeeDTOs.get(0).getCompanyAddress())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].email", Matchers.is(employeeDTOs.get(1).getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].companyName", Matchers.is(employeeDTOs.get(1).getCompanyName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].companyAddress", Matchers.is(employeeDTOs.get(1).getCompanyAddress())));
    }

    @Test
    void findEmployeesDTOByCompanyAddressAndNotGivenCompanyName() throws Exception {
        Mockito.when(employeeService.getEmployeesDTOByCompanyAddress(sameCompanyAddressAndDiffCompanyName.get(0).getCompany().getAddress()))
                .thenReturn(sameCompanyAddressAndDiffCompanyNameDTOs);

        mockMvc.perform(MockMvcRequestBuilders.get("/employee/company?companyAddress=" + sameCompanyAddressAndDiffCompanyName.get(0).getCompany().getAddress()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email", Matchers.is(sameCompanyAddressAndDiffCompanyNameDTOs.get(0).getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyName", Matchers.is(sameCompanyAddressAndDiffCompanyNameDTOs.get(0).getCompanyName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyAddress", Matchers.is(sameCompanyAddressAndDiffCompanyNameDTOs.get(0).getCompanyAddress())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].email", Matchers.is(sameCompanyAddressAndDiffCompanyNameDTOs.get(1).getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].companyName", Matchers.is(sameCompanyAddressAndDiffCompanyNameDTOs.get(1).getCompanyName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].companyAddress", Matchers.is(sameCompanyAddressAndDiffCompanyNameDTOs.get(1).getCompanyAddress())));
    }

    @Test
    void findEmployeesDTOByNotGivenCompanyNameAndNotGivenCompanyAddress() throws Exception {
        Mockito.when(employeeService.getEmployeesDTO()).thenReturn(employeeDTOs);

        mockMvc.perform(get("/employee/company?"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is(employeeDTOs.get(0).getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email", Matchers.is(employeeDTOs.get(0).getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyAddress", Matchers.is(employeeDTOs.get(0).getCompanyAddress())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].devicesCount", Matchers.is(employeeDTOs.get(0).getDevicesCount())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", Matchers.is(employeeDTOs.get(1).getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].email", Matchers.is(employeeDTOs.get(1).getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].companyAddress", Matchers.is(employeeDTOs.get(1).getCompanyAddress())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].devicesCount", Matchers.is(employeeDTOs.get(1).getDevicesCount())));
    }

    @Test
    void addNotExistingEmployee() throws Exception {
        Mockito.when(employeeService.saveEmployeeDTO(any(Employee.class))).thenReturn(employeeDTO1);

        mockMvc.perform(post("/employee/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee1)))
                .andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(employeeDTO1)))
                .andReturn();
    }

    @Test
    void addExistingEmployee() throws Exception {
        Mockito.when(employeeService.getEmployeeDTOByEmail(employee1.getEmail())).thenReturn(employeeDTO1);

        mockMvc.perform(post("/employee/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee1)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Employee with mail: " + employee1.getEmail() + " already exists!"))
                .andReturn();
    }

    @Test
    void addNotExistingEmployees() throws Exception {
        Mockito.when(employeeService.saveEmployeesDTO(any())).thenReturn(employeeDTOs);

        mockMvc.perform(post("/employee/addMany")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employees)))
                .andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(employeeDTOs)))
                .andReturn();
    }

    @Test
    void addExistingEmployees() throws Exception {
        Mockito.when(employeeService.getEmployeeDTOByEmail(employees.get(1).getEmail())).thenReturn(employeeDTOs.get(1));

        mockMvc.perform(post("/employee/addMany")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employees)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Employee with mail: " + employees.get(1).getEmail() + " already exists!"))
                .andReturn();
    }

    @Test
    void updateExistingEmployee() throws Exception {
        Mockito.when(employeeService.updateEmployeeDTO(any(Employee.class))).thenReturn(employeeDTO1);

        mockMvc.perform(MockMvcRequestBuilders.put("/employee/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee1)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(employeeDTO1)))
                .andReturn();
    }

    @Test
    void updateNotExistingEmployee() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/employee/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee1)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Employee with id: " + employee1.getId() + " does not exist!"))
                .andReturn();
    }

    @Test
    void updateExistingEmployees() throws Exception {
        Mockito.when(employeeService.updateEmployeesDTO(any())).thenReturn(employeeDTOs);

        mockMvc.perform(MockMvcRequestBuilders.put("/employee/updateMany")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employees)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(employeeDTOs)))
                .andReturn();
    }

    @Test
    void updateNotExistingEmployees() throws Exception {
        Mockito.when(employeeService.updateEmployeesDTO(any())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.put("/employee/updateMany")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employees)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("At least one of the employees does not exist!"))
                .andReturn();

    }

    @Test
    void deleteExistingEmployee() throws Exception {
        String expectedResponse = "Employee with id: " + employee1.getId() + " is successfully removed";

        Mockito.when(employeeService.getEmployeeById(employee1.getId())).thenReturn(employeeDTO1);
        Mockito.when(employeeService.deleteEmployeeById(employee1.getId())).thenReturn(expectedResponse);

        mockMvc.perform(delete("/employee/delete/" + employee1.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse))
                .andReturn();
    }

    @Test
    void deleteNotExistingEmployee() throws Exception {
        mockMvc.perform(delete("/employee/delete/" + employee1.getId()))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Employee with id: " + employee1.getId() + " does not exist!"))
                .andReturn();
    }
}