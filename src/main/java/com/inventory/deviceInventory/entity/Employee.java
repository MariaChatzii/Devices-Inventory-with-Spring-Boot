package com.inventory.deviceInventory.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
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
    @NotEmpty(message = "Name may not be empty")
    @NotBlank(message = "Name may not be blank")
    private String name;

    @Column(name = "email", unique = true, nullable = false, length = 45)
    @NotEmpty(message = "Email may not be empty")
    @NotBlank(message = "Email may not be blank")
    private String email;

    @OneToMany(mappedBy = "employeeOwner")
    private List<Device> devices;

    @ManyToOne
    @JoinColumn(name = "company_id",  nullable = false)
    private Company company;

}
