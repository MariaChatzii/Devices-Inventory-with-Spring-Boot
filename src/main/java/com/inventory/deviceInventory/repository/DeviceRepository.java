package com.inventory.deviceInventory.repository;

import com.inventory.deviceInventory.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, String> {
}
