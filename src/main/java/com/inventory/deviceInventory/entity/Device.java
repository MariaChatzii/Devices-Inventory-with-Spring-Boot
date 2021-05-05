package com.inventory.deviceInventory.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.inventory.deviceInventory.EmployeeJsonDeserializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import javax.persistence.*;
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
    @NonNull
    private String name;

    @Column(name = "type", nullable = false, length = 255)
    @NonNull
    private String type;

    @JsonBackReference
    @JsonDeserialize(using = EmployeeJsonDeserializer.class)
    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employeeOwner;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company companyOwner;
}
