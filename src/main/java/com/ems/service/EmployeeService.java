package com.ems.service;

import com.ems.entity.Employee;
import java.util.List;

public interface EmployeeService {
    Employee create(String xml);
    Employee update(Long id, Employee emp);
    void delete(Long id);
    Employee getById(Long id);
    List<Employee> getAll();
}