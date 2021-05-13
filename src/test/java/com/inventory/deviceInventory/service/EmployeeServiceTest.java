package com.inventory.deviceInventory.service;

import com.inventory.deviceInventory.DTO.DeviceDTO;
import com.inventory.deviceInventory.DTO.EmployeeDTO;
import com.inventory.deviceInventory.entity.Company;
import com.inventory.deviceInventory.entity.Device;
import com.inventory.deviceInventory.entity.Employee;
import com.inventory.deviceInventory.mapper.DTOMapper;
import com.inventory.deviceInventory.repository.DeviceRepository;
import com.inventory.deviceInventory.repository.EmployeeRepository;
import org.assertj.core.api.Assertions;
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
class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private DTOMapper dtoMapper;
    @Captor
    private ArgumentCaptor<Employee> employeeArgumentCaptor;
    @Captor
    private ArgumentCaptor<List<Employee>> employeesArgumentCaptor;


    @Test
    void getEmployeesDTO() {
        List<DeviceDTO> devices = new ArrayList<>();

        List<EmployeeDTO> expectedResponse = new ArrayList<>();
        EmployeeDTO employeeDTO1 = new EmployeeDTO("John John", "john@gm.com", devices, "mycompany", "anywhere 12, Mexico", 0);
        EmployeeDTO employeeDTO2 = new EmployeeDTO("Maria Jackson", "mar@gm.com", devices, "mycompany2", "anywhere 3, Greece", 1);

        expectedResponse.add(employeeDTO1);
        expectedResponse.add(employeeDTO2);

        Mockito.when(dtoMapper.employeesToEmployeeDTOs(employeeRepository.findAll())).thenReturn(expectedResponse);

        List<EmployeeDTO> actualResponse = employeeService.getEmployeesDTO();

        assertEquals(expectedResponse.size(), actualResponse.size());
    }

    @Test
    void getEmployeeById() {
        List<Device> devices = new ArrayList<>();
        List<Employee> employees = new ArrayList<>();

        Company company = new Company(5,"mycompany", "anywhere 12, Mexico", employees, devices);
        Employee employee = new Employee(100, "Javie Rojas", "javie@g.com", devices, company);

        EmployeeDTO expectedResponse = new EmployeeDTO("Javie Rojas", "javie@gm.com", dtoMapper.devicesToDeviceDTOs(devices), "mycompany", "anywhere 12, Mexico", 0);

        Mockito.when(employeeRepository.findById(100)).thenReturn(Optional.of(employee));
        Mockito.when(dtoMapper.employeeToEmployeeDTO(Mockito.any(Employee.class))).thenReturn(expectedResponse);

        EmployeeDTO actualResponse = employeeService.getEmployeeById(100);

        assertEquals(expectedResponse.getName(), actualResponse.getName());
        assertEquals(expectedResponse.getEmail(), actualResponse.getEmail());
        assertEquals(expectedResponse.getCompanyName(), actualResponse.getCompanyName());
        assertEquals(expectedResponse.getCompanyAddress(), actualResponse.getCompanyAddress());
        assertEquals(expectedResponse.getDevices(), actualResponse.getDevices());
        assertEquals(expectedResponse.getDevicesCount(), actualResponse.getDevicesCount());
    }

    @Test
    void getEmployeeDTOByEmail() {
        List<Device> devices = new ArrayList<>();
        List<Employee> employees = new ArrayList<>();

        Company company = new Company(5,"mycompany", "anywhere 12, Mexico", employees, devices);
        Employee employee = new Employee(100, "Javie Rojas", "javie@gm.com", devices, company);

        EmployeeDTO expectedResponse = new EmployeeDTO("Javie Rojas", "javie@gm.com", dtoMapper.devicesToDeviceDTOs(devices), "mycompany", "anywhere 12, Mexico", 0);

        Mockito.when(employeeRepository.findByEmail("javie@gm.com")).thenReturn(employee);
        Mockito.when(dtoMapper.employeeToEmployeeDTO(Mockito.any(Employee.class))).thenReturn(expectedResponse);

        EmployeeDTO actualResponse = employeeService.getEmployeeDTOByEmail(employee.getEmail());

        assertEquals(expectedResponse.getName(), actualResponse.getName());
        assertEquals(expectedResponse.getEmail(), actualResponse.getEmail());
        assertEquals(expectedResponse.getCompanyName(), actualResponse.getCompanyName());
        assertEquals(expectedResponse.getCompanyAddress(), actualResponse.getCompanyAddress());
        assertEquals(expectedResponse.getDevices(), actualResponse.getDevices());
        assertEquals(expectedResponse.getDevicesCount(), actualResponse.getDevicesCount());
    }

    @Test
    void getEmployeesDTOByName() {
        List<Device> devices = new ArrayList<>();

        EmployeeDTO expectedResponse1 = new EmployeeDTO("Javie Rojas", "javie@gm.com", dtoMapper.devicesToDeviceDTOs(devices), "mycompany", "anywhere 12, Mexico", 0);
        EmployeeDTO expectedResponse2 = new EmployeeDTO("Javie Rojas", "rojas@gm.com", dtoMapper.devicesToDeviceDTOs(devices), "mycompany", "anywhere 12, Mexico", 0);

        List<EmployeeDTO> expectedResponse = new ArrayList<>();
        expectedResponse.add(expectedResponse1);
        expectedResponse.add(expectedResponse2);

        Mockito.when(dtoMapper.employeesToEmployeeDTOs(employeeRepository.findByName("Javie Rojas"))).thenReturn(expectedResponse);

        List<EmployeeDTO> actualResponse = employeeService.getEmployeesDTOByName("Javie Rojas");

        assertEquals(expectedResponse.size(), actualResponse.size());
        assertEquals(expectedResponse.get(0).getName(), actualResponse.get(0).getName());
        assertEquals(expectedResponse.get(0).getEmail(), actualResponse.get(0).getEmail());
        assertEquals(expectedResponse.get(1).getName(), actualResponse.get(1).getName());
        assertEquals(expectedResponse.get(1).getEmail(), actualResponse.get(1).getEmail());
    }

    @Test
    void getEmployeesDTOByCompanyName() {
        EmployeeDTO employeeDTO1 = new EmployeeDTO("Samsung Galaxy A7 Tab", "tablet", new ArrayList<>(), "mycompany", "anywhere 12, Mexico",  0);
        EmployeeDTO employeeDTO2 = new EmployeeDTO("Samsung Galaxy S21", "mobile", new ArrayList<>(), "mycompany", "anywhere 12, Mexico", 0);

        List<EmployeeDTO> expectedResponse = new ArrayList<>();
        expectedResponse.add(employeeDTO1);
        expectedResponse.add(employeeDTO2);

        Mockito.when(dtoMapper.employeesToEmployeeDTOs(employeeRepository.findByCompanyName("mycompany"))).thenReturn(expectedResponse);

        List<EmployeeDTO> actualResponse = employeeService.getEmployeesDTOByCompanyName("mycompany");

        assertEquals(expectedResponse.size(), actualResponse.size());
        assertEquals(expectedResponse.get(0).getEmail(), actualResponse.get(0).getEmail());
        assertEquals(expectedResponse.get(0).getName(), actualResponse.get(0).getName());
        assertEquals(expectedResponse.get(1).getEmail(), actualResponse.get(1).getEmail());
        assertEquals(expectedResponse.get(1).getName(), actualResponse.get(1).getName());
    }

    @Test
    void getEmployeesDTOByCompanyAddress() {
        EmployeeDTO employeeDTO1 = new EmployeeDTO("Samsung Galaxy A7 Tab", "tablet", new ArrayList<>(), "mycompany", "anywhere 12, Mexico",  0);
        EmployeeDTO employeeDTO2 = new EmployeeDTO("Samsung Galaxy S21", "mobile", new ArrayList<>(), "mycompany", "anywhere 12, Mexico", 0);

        List<EmployeeDTO> expectedResponse = new ArrayList<>();
        expectedResponse.add(employeeDTO1);
        expectedResponse.add(employeeDTO2);

        Mockito.when(dtoMapper.employeesToEmployeeDTOs(employeeRepository.findByCompanyAddress("anywhere 12, Mexico"))).thenReturn(expectedResponse);

        List<EmployeeDTO> actualResponse = employeeService.getEmployeesDTOByCompanyAddress("anywhere 12, Mexico");

        assertEquals(expectedResponse.size(), actualResponse.size());
        assertEquals(expectedResponse.get(0).getEmail(), actualResponse.get(0).getEmail());
        assertEquals(expectedResponse.get(0).getName(), actualResponse.get(0).getName());
        assertEquals(expectedResponse.get(1).getEmail(), actualResponse.get(1).getEmail());
        assertEquals(expectedResponse.get(1).getName(), actualResponse.get(1).getName());
    }

    @Test
    void getEmployeesDTOByCompanyNameAndCompanyAddress() {
        EmployeeDTO employeeDTO1 = new EmployeeDTO("Samsung Galaxy A7 Tab", "tablet", new ArrayList<>(), "mycompany", "anywhere 12, Mexico",  0);
        EmployeeDTO employeeDTO2 = new EmployeeDTO("Samsung Galaxy S21", "mobile", new ArrayList<>(), "mycompany", "anywhere 12, Mexico", 0);

        List<EmployeeDTO> expectedResponse = new ArrayList<>();
        expectedResponse.add(employeeDTO1);
        expectedResponse.add(employeeDTO2);

        Mockito.when(dtoMapper.employeesToEmployeeDTOs(employeeRepository.findByCompanyNameAndCompanyAddress("mycompany", "anywhere 12, Mexico"))).thenReturn(expectedResponse);

        List<EmployeeDTO> actualResponse = employeeService.getEmployeesDTOByCompanyNameAndCompanyAddress("mycompany", "anywhere 12, Mexico");

        assertEquals(expectedResponse.size(), actualResponse.size());
        assertEquals(expectedResponse.get(0).getEmail(), actualResponse.get(0).getEmail());
        assertEquals(expectedResponse.get(0).getName(), actualResponse.get(0).getName());
        assertEquals(expectedResponse.get(1).getEmail(), actualResponse.get(1).getEmail());
        assertEquals(expectedResponse.get(1).getName(), actualResponse.get(1).getName());
    }

    @Test
    void saveEmployeeDTO() {
        Employee employee = new Employee(100, "Javie Rojas", "javie@gm.com", new ArrayList<>(), new Company());
        EmployeeDTO expectedResponse = new EmployeeDTO("Javie Rojas", "javie@gm.com", new ArrayList<>(), "mycompany", "anywhere 12, Mexico", 0);

        Mockito.when(dtoMapper.employeeToEmployeeDTO(employeeRepository.save(employee))).thenReturn(expectedResponse);

        EmployeeDTO actualResponse = employeeService.saveEmployeeDTO(employee);
        Mockito.verify(employeeRepository, Mockito.times(2)).save(employeeArgumentCaptor.capture());

        Assertions.assertThat(employeeArgumentCaptor.getValue().getName()).isEqualTo(employee.getName());
        Assertions.assertThat(employeeArgumentCaptor.getValue().getEmail()).isEqualTo(employee.getEmail());

        assertEquals(expectedResponse.getEmail(), actualResponse.getEmail());
    }

    @Test
    void saveEmployeesDTO() {
        Employee employee1 = new Employee(100, "Javie Rojas", "javie@gm.com", new ArrayList<>(), new Company());
        EmployeeDTO expectedResponse1 = new EmployeeDTO("Javie Rojas", "javie@gm.com", new ArrayList<>(), "mycompany", "anywhere 12, Mexico", 0);
        Employee employee2 = new Employee(101, "Alex Rojas", "rojas@gm.com", new ArrayList<>(), new Company());
        EmployeeDTO expectedResponse2 = new EmployeeDTO("Alex Rojas", "rojas@gm.com", new ArrayList<>(), "mycompany", "anywhere 12, Mexico", 0);

        List<EmployeeDTO> expectedResponse = new ArrayList<>();
        expectedResponse.add(expectedResponse1);
        expectedResponse.add(expectedResponse2);

        Mockito.when(dtoMapper.employeesToEmployeeDTOs(employeeRepository.saveAll(Stream.of(employee1, employee2).collect(Collectors.toList())))).thenReturn(expectedResponse);

        employeeService.saveEmployeesDTO(Stream.of(employee1, employee2).collect(Collectors.toList()));
        Mockito.verify(employeeRepository, Mockito.times(2)).saveAll(employeesArgumentCaptor.capture());

        Assertions.assertThat(employeesArgumentCaptor.getValue().get(0).getId()).isEqualTo(employee1.getId());
        Assertions.assertThat(employeesArgumentCaptor.getValue().get(0).getEmail()).isEqualTo(employee1.getEmail());
        Assertions.assertThat(employeesArgumentCaptor.getValue().get(1).getId()).isEqualTo(employee2.getId());
        Assertions.assertThat(employeesArgumentCaptor.getValue().get(1).getEmail()).isEqualTo(employee2.getEmail());
    }

    @Test
    void updateExistingEmployeeDTO() {
        Employee existingEmployee = new Employee(100, "Javie Rojas", "javie@gm.com", new ArrayList<>(), new Company());
        Employee updateEmployee = new Employee(100, "Alex Rojas", "alex@gm.com", new ArrayList<>(), new Company());

        EmployeeDTO expectedResponse = new EmployeeDTO("Alex Rojas", "alex@gm.com", new ArrayList<>(), "mycompany", "anywhere 12, Mexico", 0);

        Mockito.when(employeeRepository.findById(100)).thenReturn(Optional.of(existingEmployee));
        Mockito.when(dtoMapper.employeeToEmployeeDTO(employeeRepository.save(updateEmployee))).thenReturn(expectedResponse);

        employeeService.updateEmployeeDTO(updateEmployee);
        Mockito.verify(employeeRepository, Mockito.times(2)).save(employeeArgumentCaptor.capture());

        Assertions.assertThat(employeeArgumentCaptor.getValue().getId()).isEqualTo(updateEmployee.getId());
        Assertions.assertThat(employeeArgumentCaptor.getValue().getEmail()).isEqualTo(updateEmployee.getEmail());
    }

    @Test
    void updateNotExistingEmployeeDTO() {
        Employee updateEmployee = new Employee(100, "Javie Rojas", "javie@gm.com", new ArrayList<>(), new Company());

        EmployeeDTO actualResponse = employeeService.updateEmployeeDTO(updateEmployee);
        assertNull(actualResponse);
        Mockito.verify(employeeRepository, Mockito.times(0)).save(employeeArgumentCaptor.capture());
    }

    @Test
    void updateExistingEmployeesDTO() {
        Employee existingEmployee1 = new Employee(100, "Javie Rojas", "javie@gm.com", new ArrayList<>(), new Company());
        Employee existingEmployee2 = new Employee(101, "Maria Jackson", "maria@gm.com", new ArrayList<>(), new Company());

        Employee updateEmployee1 = new Employee(100, "Alex Rojas", "alex@gm.com", new ArrayList<>(), new Company());
        Employee updateEmployee2 = new Employee(101, "Maria Jackson", "maria@gm.com", new ArrayList<>(), new Company());

        EmployeeDTO expectedResponse1 = new EmployeeDTO("Alex Rojas", "alex@gm.com", new ArrayList<>(), "mycompany", "anywhere 12, Mexico", 0);
        EmployeeDTO expectedResponse2 = new EmployeeDTO("Maria Jackson", "maria@gm.com", new ArrayList<>(), "mycompany", "anywhere 12, Mexico", 0);

        List<Employee> updateEmployees = new ArrayList<>();
        updateEmployees.add(updateEmployee1);
        updateEmployees.add(updateEmployee2);

        List<EmployeeDTO> expectedResponse = new ArrayList<>();
        expectedResponse.add(expectedResponse1);
        expectedResponse.add(expectedResponse2);

        Mockito.when(employeeRepository.findById(100)).thenReturn(Optional.of(existingEmployee1));
        Mockito.when(employeeRepository.findById(101)).thenReturn(Optional.of(existingEmployee2));

        Mockito.when(dtoMapper.employeesToEmployeeDTOs(employeeRepository.saveAll(updateEmployees))).thenReturn(expectedResponse);

        employeeService.updateEmployeesDTO(updateEmployees);
        Mockito.verify(employeeRepository, Mockito.times(2)).saveAll(employeesArgumentCaptor.capture());

        Assertions.assertThat(employeesArgumentCaptor.getValue().get(0).getId()).isEqualTo(updateEmployee1.getId());
        Assertions.assertThat(employeesArgumentCaptor.getValue().get(0).getEmail()).isEqualTo(updateEmployee1.getEmail());
        Assertions.assertThat(employeesArgumentCaptor.getValue().get(1).getId()).isEqualTo(updateEmployee2.getId());
        Assertions.assertThat(employeesArgumentCaptor.getValue().get(1).getEmail()).isEqualTo(updateEmployee2.getEmail());
    }

    @Test
    void updateNotExistingEmployeesDTO() {
        Employee existingEmployee = new Employee(100, "Javie Rojas", "javie@gm.com", new ArrayList<>(), new Company());

        Employee updateEmployee1 = new Employee(100, "Alex Rojas", "alex@gm.com", new ArrayList<>(), new Company());
        Employee updateEmployee2 = new Employee(101, "Maria Jackson", "maria@gm.com", new ArrayList<>(), new Company());

        List<Employee> updateEmployees = new ArrayList<>();
        updateEmployees.add(updateEmployee1);
        updateEmployees.add(updateEmployee2);

        Mockito.when(employeeRepository.findById(updateEmployee1.getId())).thenReturn(Optional.of(existingEmployee));

        List<EmployeeDTO> actualResponse = employeeService.updateEmployeesDTO(updateEmployees);
        assertNull(actualResponse);
        Mockito.verify(employeeRepository, Mockito.times(0)).saveAll(employeesArgumentCaptor.capture());
    }

    @Test
    void deleteEmployeeById() {
        int id = 100;

        String actualResponse = employeeService.deleteEmployeeById(id);
        Mockito.verify(employeeRepository, Mockito.times(1)).deleteById(id);

        assertEquals("Employee with id: " + id + " is successfully removed!", actualResponse);
    }
}