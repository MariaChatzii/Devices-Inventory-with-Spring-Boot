package com.inventory.deviceInventory.DTO;

import lombok.Data;

@Data
public class DeviceDTO {

    private String name;
    private String type;
    private String companyOwnerName;
    private String companyOwnerAddress;
    private String employeeOwnerName;
    private String employeeOwnerEmail;
}
