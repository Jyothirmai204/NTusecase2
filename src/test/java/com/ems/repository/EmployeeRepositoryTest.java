package com.ems.repository;

import com.ems.entity.Employee;
import com.ems.Main;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = Main.class)
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository repo;

    // ✅ SAVE
    @Test
    void testSaveEmployee() {

        Employee emp = new Employee();
        emp.setName("John");
        emp.setEmail("john@gmail.com");
        emp.setDepartment("IT");
        emp.setDateOfJoining("2024-01-01");

        // ✅ REQUIRED FIELDS
        emp.setPhone("9876543210");
        emp.setSalary(50000.0);
        emp.setStatus("ACTIVE");

        Employee saved = repo.save(emp);

        assertNotNull(saved.getId());
        assertEquals("John", saved.getName());
    }

    // ✅ FIND
    @Test
    void testFindById() {

        Employee emp = new Employee();
        emp.setName("Test");
        emp.setEmail("test@gmail.com");
        emp.setDepartment("HR");
        emp.setDateOfJoining("2024-01-01");

        // ✅ REQUIRED FIELDS
        emp.setPhone("9999999999");
        emp.setSalary(40000.0);
        emp.setStatus("ACTIVE");

        Employee saved = repo.save(emp);

        Optional<Employee> result = repo.findById(saved.getId());

        assertTrue(result.isPresent());
        assertEquals("Test", result.get().getName());
    }

    // ✅ DELETE
    @Test
    void testDeleteEmployee() {

        Employee emp = new Employee();
        emp.setName("Delete");
        emp.setEmail("delete@gmail.com");
        emp.setDepartment("Admin");
        emp.setDateOfJoining("2024-01-01");

        // ✅ REQUIRED FIELDS
        emp.setPhone("8888888888");
        emp.setSalary(30000.0);
        emp.setStatus("INACTIVE");

        Employee saved = repo.save(emp);

        repo.deleteById(saved.getId());

        Optional<Employee> result = repo.findById(saved.getId());

        assertFalse(result.isPresent());
    }
}
