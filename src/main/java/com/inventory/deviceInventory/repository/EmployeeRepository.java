package com.inventory.deviceInventory.repository;

import com.inventory.deviceInventory.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
    Employee findByEmail(String email);

    List<Employee> findByName(String name);

    List<Employee> findByCompanyNameAndCompanyAddress(String companyName, String companyAddress);
}
