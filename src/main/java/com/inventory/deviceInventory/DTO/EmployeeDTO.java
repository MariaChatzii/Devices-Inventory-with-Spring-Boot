package com.inventory.deviceInventory.DTO;

import lombok.Data;

import java.util.List;

@Data
public class EmployeeDTO {

    private String name;
    private String email;
    private List<DeviceDTO> devices;
    private String companyName;
    private String companyAddress;
    private Integer devicesCount;
}
