package com.inventory.deviceInventory.DTO;

import com.fasterxml.jackson.annotation.JsonView;
import com.inventory.deviceInventory.View;
import lombok.Data;

@Data
public class DeviceDTO {

    @JsonView(View.WithoutDeviceCompanyAndEmployeeInfo.class)
    private String name;
    @JsonView(View.WithoutDeviceCompanyAndEmployeeInfo.class)
    private String type;
    private String companyOwnerName;
    private String companyOwnerAddress;
    private String employeeOwnerName;
    private String employeeOwnerEmail;
}
