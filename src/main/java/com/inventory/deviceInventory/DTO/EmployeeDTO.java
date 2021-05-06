package com.inventory.deviceInventory.DTO;


import com.inventory.deviceInventory.entity.Device;
import lombok.Data;

import java.util.List;

@Data
public class EmployeeDTO {

    private String name;
    private String email;
    private List<Device> devices;
    private String companyName;
    private String companyAddress;
    private Integer devicesCount;
}
