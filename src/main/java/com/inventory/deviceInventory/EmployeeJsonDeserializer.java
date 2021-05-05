package com.inventory.deviceInventory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.inventory.deviceInventory.entity.Employee;

import java.io.IOException;

public class EmployeeJsonDeserializer extends JsonDeserializer<Employee> {
    @Override
    public Employee deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException, IOException {
        if(jsonParser == null)return null;

        Employee employee = new Employee();
        employee.setId(Integer.valueOf(jsonParser.getText()));
        return employee;
    }
}