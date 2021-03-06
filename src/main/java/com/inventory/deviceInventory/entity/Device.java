package com.inventory.deviceInventory.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "device")
public class Device implements Serializable {

    @Id
    private String serialNumber;

    @Column(name = "name", nullable = false, length = 255)
    @NotEmpty(message = "Name may not be empty")
    @NotBlank(message = "Name may not be blank")
    private String name;

    @Column(name = "type", nullable = false, length = 255)
    @NotEmpty(message = "Type may not be empty")
    @NotBlank(message = "Type may not be blank")
    private String type;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employeeOwner;

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id", nullable = false)
    private Company companyOwner;

    public boolean hasSameCompanyWithDeviceEmployeeOwnerCompany(Employee employee){
        if (employee != null)
            //if device is given to an existing employee and this employee belongs to the same company with the device
            if (getCompanyOwner().getId().equals(employee.getCompany().getId()))
                return true;
        return false;
    }

}
