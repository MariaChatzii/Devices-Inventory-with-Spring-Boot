package com.inventory.deviceInventory.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employee")
public class Employee {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, length = 45)
    @NonNull
    private String name;

    @Column(name = "email", unique = true, nullable = false, length = 45)
    @NonNull
    private String email;

    //@JsonManagedReference
    @OneToMany(mappedBy = "employeeOwner")
    private List<Device> devices;

    @ManyToOne
    @JoinColumn(name = "company_id",  nullable = false)
    private Company company;

}
