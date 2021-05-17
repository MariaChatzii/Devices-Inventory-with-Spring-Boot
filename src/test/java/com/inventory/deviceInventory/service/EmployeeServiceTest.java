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


    private Employee employee1;
    private EmployeeDTO employeeDTO1;
    private List<Employee> employees;
    private List<EmployeeDTO> employeeDTOs, sameNameEmployeeDTOs, sameCompanyEmployeeDTOs, sameCompanyAddressAndDiffCompanyNameEmployeeDTOs, sameCompanyNameAndDiffCompanyAddressEmployeeDTOs;

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

        sameNameEmployeeDTOs = Stream.of(employeeDTO1, employeeDTO3).collect(Collectors.toList());
        sameCompanyEmployeeDTOs = Stream.of(employeeDTO1, employeeDTO3).collect(Collectors.toList());
        sameCompanyAddressAndDiffCompanyNameEmployeeDTOs = (Stream.of(employeeDTO2, employeeDTO4).collect(Collectors.toList()));
        sameCompanyNameAndDiffCompanyAddressEmployeeDTOs = Stream.of(employeeDTO1, employeeDTO2).collect(Collectors.toList());
    }

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
       Mockito.when(employeeRepository.findById(employee1.getId())).thenReturn(Optional.of(employee1));
        Mockito.when(dtoMapper.employeeToEmployeeDTO(Mockito.any(Employee.class))).thenReturn(employeeDTO1);

        EmployeeDTO actualResponse = employeeService.getEmployeeById(employee1.getId());

        assertEquals(employeeDTO1.getName(), actualResponse.getName());
        assertEquals(employeeDTO1.getEmail(), actualResponse.getEmail());
        assertEquals(employeeDTO1.getCompanyName(), actualResponse.getCompanyName());
        assertEquals(employeeDTO1.getCompanyAddress(), actualResponse.getCompanyAddress());
        assertEquals(employeeDTO1.getDevices(), actualResponse.getDevices());
        assertEquals(employeeDTO1.getDevicesCount(), actualResponse.getDevicesCount());
    }

    @Test
    void getEmployeeDTOByEmail() {
        Mockito.when(employeeRepository.findByEmail(employee1.getEmail())).thenReturn(employee1);
        Mockito.when(dtoMapper.employeeToEmployeeDTO(Mockito.any(Employee.class))).thenReturn(employeeDTO1);

        EmployeeDTO actualResponse = employeeService.getEmployeeDTOByEmail(employee1.getEmail());

        assertEquals(employeeDTO1.getName(), actualResponse.getName());
        assertEquals(employeeDTO1.getEmail(), actualResponse.getEmail());
        assertEquals(employeeDTO1.getCompanyName(), actualResponse.getCompanyName());
        assertEquals(employeeDTO1.getCompanyAddress(), actualResponse.getCompanyAddress());
        assertEquals(employeeDTO1.getDevices(), actualResponse.getDevices());
        assertEquals(employeeDTO1.getDevicesCount(), actualResponse.getDevicesCount());
    }

    @Test
    void getEmployeesDTOByName() {
        Mockito.when(dtoMapper.employeesToEmployeeDTOs(employeeRepository.findByName(employees.get(0).getName()))).thenReturn(employeeDTOs);

        List<EmployeeDTO> actualResponse = employeeService.getEmployeesDTOByName(employees.get(0).getName());

        assertEquals(employeeDTOs.size(), actualResponse.size());
        assertEquals(employeeDTOs.get(0).getName(), actualResponse.get(0).getName());
        assertEquals(employeeDTOs.get(0).getEmail(), actualResponse.get(0).getEmail());
        assertEquals(employeeDTOs.get(1).getName(), actualResponse.get(1).getName());
        assertEquals(employeeDTOs.get(1).getEmail(), actualResponse.get(1).getEmail());
    }

    @Test
    void getEmployeesDTOByCompanyName() {
        Mockito.when(dtoMapper.employeesToEmployeeDTOs(employeeRepository.findByCompanyName(sameCompanyNameAndDiffCompanyAddressEmployeeDTOs.get(0).getCompanyName())))
                .thenReturn(sameCompanyNameAndDiffCompanyAddressEmployeeDTOs);

        List<EmployeeDTO> actualResponse = employeeService.getEmployeesDTOByCompanyName(sameCompanyNameAndDiffCompanyAddressEmployeeDTOs.get(0).getCompanyName());

        assertEquals(sameCompanyNameAndDiffCompanyAddressEmployeeDTOs.size(), actualResponse.size());
        assertEquals(sameCompanyNameAndDiffCompanyAddressEmployeeDTOs.get(0).getEmail(), actualResponse.get(0).getEmail());
        assertEquals(sameCompanyNameAndDiffCompanyAddressEmployeeDTOs.get(0).getName(), actualResponse.get(0).getName());
        assertEquals(sameCompanyNameAndDiffCompanyAddressEmployeeDTOs.get(1).getEmail(), actualResponse.get(1).getEmail());
        assertEquals(sameCompanyNameAndDiffCompanyAddressEmployeeDTOs.get(1).getName(), actualResponse.get(1).getName());
    }

    @Test
    void getEmployeesDTOByCompanyAddress() {
        Mockito.when(dtoMapper.employeesToEmployeeDTOs(employeeRepository.findByCompanyAddress(sameCompanyAddressAndDiffCompanyNameEmployeeDTOs.get(0).getCompanyAddress())))
                .thenReturn(sameCompanyAddressAndDiffCompanyNameEmployeeDTOs);

        List<EmployeeDTO> actualResponse = employeeService.getEmployeesDTOByCompanyAddress(sameCompanyAddressAndDiffCompanyNameEmployeeDTOs.get(0).getCompanyAddress());

        assertEquals(sameCompanyAddressAndDiffCompanyNameEmployeeDTOs.size(), actualResponse.size());
        assertEquals(sameCompanyAddressAndDiffCompanyNameEmployeeDTOs.get(0).getEmail(), actualResponse.get(0).getEmail());
        assertEquals(sameCompanyAddressAndDiffCompanyNameEmployeeDTOs.get(0).getName(), actualResponse.get(0).getName());
        assertEquals(sameCompanyAddressAndDiffCompanyNameEmployeeDTOs.get(1).getEmail(), actualResponse.get(1).getEmail());
        assertEquals(sameCompanyAddressAndDiffCompanyNameEmployeeDTOs.get(1).getName(), actualResponse.get(1).getName());
    }

    @Test
    void getEmployeesDTOByCompanyNameAndCompanyAddress() {
        Mockito.when(dtoMapper.employeesToEmployeeDTOs(employeeRepository.findByCompanyName(sameCompanyEmployeeDTOs.get(0).getCompanyName())))
                .thenReturn(sameCompanyEmployeeDTOs);

        List<EmployeeDTO> actualResponse = employeeService.getEmployeesDTOByCompanyName(sameCompanyEmployeeDTOs.get(0).getCompanyName());

        assertEquals(sameCompanyEmployeeDTOs.size(), actualResponse.size());
        assertEquals(sameCompanyEmployeeDTOs.get(0).getEmail(), actualResponse.get(0).getEmail());
        assertEquals(sameCompanyEmployeeDTOs.get(0).getName(), actualResponse.get(0).getName());
        assertEquals(sameCompanyEmployeeDTOs.get(1).getEmail(), actualResponse.get(1).getEmail());
        assertEquals(sameCompanyEmployeeDTOs.get(1).getName(), actualResponse.get(1).getName());
    }

    @Test
    void saveEmployeeDTO() {
        Mockito.when(dtoMapper.employeeToEmployeeDTO(employeeRepository.save(employee1))).thenReturn(employeeDTO1);

        EmployeeDTO actualResponse = employeeService.saveEmployeeDTO(employee1);
        Mockito.verify(employeeRepository, Mockito.times(2)).save(employee1);

        assertEquals(employeeDTO1.getEmail(), actualResponse.getEmail());
    }

    @Test
    void saveEmployeesDTO() {
        Mockito.when(dtoMapper.employeesToEmployeeDTOs(employeeRepository.saveAll(employees))).thenReturn(employeeDTOs);

        employeeService.saveEmployeesDTO(employees);
        Mockito.verify(employeeRepository, Mockito.times(2)).saveAll(employees);
    }

    @Test
    void updateExistingEmployeeDTO() {
        Mockito.when(employeeRepository.findById(employee1.getId())).thenReturn(Optional.of(employee1));
        Mockito.when(dtoMapper.employeeToEmployeeDTO(employeeRepository.save(employee1))).thenReturn(employeeDTO1);

        employeeService.updateEmployeeDTO(employee1);
        Mockito.verify(employeeRepository, Mockito.times(2)).save(employee1);
    }

    @Test
    void updateNotExistingEmployeeDTO() {
        EmployeeDTO actualResponse = employeeService.updateEmployeeDTO(employee1);
        assertNull(actualResponse);
        Mockito.verify(employeeRepository, Mockito.times(0)).save(employee1);
    }

    @Test
    void updateExistingEmployeesDTO() {
        Mockito.when(employeeRepository.findById(employees.get(0).getId())).thenReturn(Optional.of(employees.get(0)));
        Mockito.when(employeeRepository.findById(employees.get(1).getId())).thenReturn(Optional.of(employees.get(1)));

        Mockito.when(dtoMapper.employeesToEmployeeDTOs(employeeRepository.saveAll(employees))).thenReturn(employeeDTOs);

        employeeService.updateEmployeesDTO(employees);
        Mockito.verify(employeeRepository, Mockito.times(2)).saveAll(employees);
    }

    @Test
    void updateNotExistingEmployeesDTO() {
        Mockito.when(employeeRepository.findById(employee1.getId())).thenReturn(Optional.of(employee1));

        List<EmployeeDTO> actualResponse = employeeService.updateEmployeesDTO(employees);
        assertNull(actualResponse);
        Mockito.verify(employeeRepository, Mockito.times(0)).saveAll(employees);
    }

    @Test
    void deleteEmployeeById() {
        String actualResponse = employeeService.deleteEmployeeById(employee1.getId());
        Mockito.verify(employeeRepository, Mockito.times(1)).deleteById(employee1.getId());

        assertEquals("Employee with id: " + employee1.getId() + " is successfully removed!", actualResponse);
    }
}