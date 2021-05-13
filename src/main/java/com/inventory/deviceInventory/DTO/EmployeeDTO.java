package com.inventory.deviceInventory.DTO;

import com.fasterxml.jackson.annotation.JsonView;
import com.inventory.deviceInventory.View;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

    @JsonView(View.WithoutDeviceCompanyAndEmployeeInfo.class)
    private String name;
    @JsonView(View.WithoutDeviceCompanyAndEmployeeInfo.class)
    private String email;
    @JsonView(View.WithoutDeviceCompanyAndEmployeeInfo.class)
    private List<DeviceDTO> devices;
    @JsonView(View.WithoutDeviceCompanyAndEmployeeInfo.class)
    private String companyName;
    @JsonView(View.WithoutDeviceCompanyAndEmployeeInfo.class)
    private String companyAddress;
    @JsonView(View.WithoutDeviceCompanyAndEmployeeInfo.class)
    private Integer devicesCount;
}
