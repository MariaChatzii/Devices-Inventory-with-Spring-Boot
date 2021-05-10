package com.inventory.deviceInventory.repository;

import com.inventory.deviceInventory.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, String> {
    List<Device> findByCompanyOwnerNameAndCompanyOwnerAddress(String companyName, String companyAddress);
    List<Device> findByNameAndType(String name, String type);
}
