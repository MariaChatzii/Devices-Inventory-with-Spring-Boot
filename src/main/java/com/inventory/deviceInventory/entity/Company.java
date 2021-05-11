package com.inventory.deviceInventory.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter

@Entity
@Table(name = "company")
public class Company {

    @Id
    private Integer id;

    @Column(name = "name", nullable = false, length = 255)
    @NonNull
    private String name;

    @Column(name = "address", nullable = false, length = 255)
    @NonNull
    private String address;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Employee> employees;

    @OneToMany(mappedBy = "companyOwner")
    private List<Device> devices;

}
