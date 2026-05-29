package com.ems.mapper;

import com.ems.dto.EmployeeRequestDTO;
import com.ems.dto.EmployeeResponseDTO;
import com.ems.entity.Employee;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EmployeeMapper {

    // ✅ DTO → ENTITY (for create/update)
    public Employee toEntity(EmployeeRequestDTO dto) {
        if (dto == null) return null;

        return new Employee(
                null,
                dto.getName(),
                dto.getEmail(),
                dto.getDepartment(),
                dto.getDateOfJoining(),
                dto.getPhone(),
                dto.getSalary(),
                dto.getStatus(),
                LocalDateTime.now()
        );
    }

    // ✅ ENTITY → RESPONSE DTO
    public EmployeeResponseDTO toDTO(Employee emp) {
        if (emp == null) return null;

        return new EmployeeResponseDTO(
                emp.getId(),
                emp.getName(),
                emp.getEmail(),
                emp.getDepartment(),
                emp.getDateOfJoining(),
                emp.getPhone(),
                emp.getSalary(),
                emp.getStatus(),
                emp.getCreatedAt()
        );
    }

    // ✅ UPDATE EXISTING ENTITY
    public void updateEntity(Employee emp, EmployeeRequestDTO dto) {

        emp.setName(dto.getName());
        emp.setEmail(dto.getEmail());
        emp.setDepartment(dto.getDepartment());
        emp.setDateOfJoining(dto.getDateOfJoining());
        emp.setPhone(dto.getPhone());
        emp.setSalary(dto.getSalary());
        emp.setStatus(dto.getStatus());
    }
}
