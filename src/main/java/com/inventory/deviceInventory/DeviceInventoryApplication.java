package com.inventory.deviceInventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.inventory.deviceInventory.repository")
public class DeviceInventoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeviceInventoryApplication.class, args);
	}

}
