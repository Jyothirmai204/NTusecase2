

package com.ems.service;

import com.ems.dto.EmployeeRequestDTO;
import com.ems.dto.EmployeeResponseDTO;
import com.ems.entity.Employee;
import com.ems.entity.AuditLog;
import com.ems.exception.BadRequestException;
import com.ems.exception.ResourceNotFoundException;
import com.ems.mapper.EmployeeMapper;
import com.ems.repository.EmployeeRepository;
import com.ems.repository.AuditLogRepository;
import com.ems.messaging.EmployeeProducer;

import com.ems.validation.XMLValidator;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;

import java.io.StringReader;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repo;
    private final EmployeeMapper mapper;
    private final XMLValidator xmlValidator;

    // ✅ AUDIT DEPENDENCIES
    private final AuditLogRepository auditRepo;
    private final EmployeeProducer producer;

    // ✅ CREATE
    @Override
    public EmployeeResponseDTO create(String xml) {
        xmlValidator.validate(xml);
        Employee emp = convertXML(xml);

        // ✅ BUSINESS VALIDATION ONLY
        if (emp.getSalary() <= 0) {
            throw new BadRequestException("Salary must be greater than 0");
        }

        if (!emp.getStatus().equalsIgnoreCase("ACTIVE") &&
                !emp.getStatus().equalsIgnoreCase("INACTIVE")) {
            throw new BadRequestException("Status must be ACTIVE or INACTIVE");
        }

        emp.setCreatedAt(LocalDateTime.now());
        Employee saved = repo.save(emp);

        // ✅ SAVE AUDIT
        saveAudit(saved.getId(), "CREATE");
        return mapper.toDTO(saved);
    }

    // ✅ UPDATE
    @Override
    public EmployeeResponseDTO update(Long id, EmployeeRequestDTO dto) {

        Employee emp = repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee with ID " + id + " not found"));

        // ✅ BUSINESS VALIDATION
        if (dto.getSalary() <= 0) {
            throw new BadRequestException("Salary must be greater than 0");
        }

        if (!dto.getStatus().equalsIgnoreCase("ACTIVE") &&
                !dto.getStatus().equalsIgnoreCase("INACTIVE")) {
            throw new BadRequestException("Status must be ACTIVE or INACTIVE");
        }

        mapper.updateEntity(emp, dto);

        Employee updated = repo.save(emp);

        // ✅ SAVE AUDIT
        saveAudit(id, "UPDATE");

        return mapper.toDTO(updated);
    }

    // ✅ DELETE
    @Override
    public void delete(Long id) {

        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Employee not found");
        }

        repo.deleteById(id);

        // ✅ SAVE AUDIT
        saveAudit(id, "DELETE");
    }

    // ✅ GET BY ID (NO AUDIT ✅)
    @Override
    public EmployeeResponseDTO getById(Long id) {

        Employee emp = repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found"));

        return mapper.toDTO(emp);
    }

    // ✅ GET ALL (NO AUDIT ✅)
    @Override
    public List<EmployeeResponseDTO> getAll() {

        List<Employee> list = repo.findAll();

        if (list.isEmpty()) {
            throw new ResourceNotFoundException("No employees found");
        }

        return list.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    // ✅ COMMON AUDIT METHOD ✅🔥
    private void saveAudit(Long empId, String action) {

        // ✅ SAVE TO DB
        AuditLog log = new AuditLog();
        log.setEmployeeId(empId);
        log.setAction(action);
        log.setSource("REST");
        log.setTimestamp(LocalDateTime.now());

        auditRepo.save(log);

        // ✅ SEND TO JMS
        producer.sendEvent(empId, action);
    }

    // ✅ XML → ENTITY
    private Employee convertXML(String xml) {

        try {
            JAXBContext context = JAXBContext.newInstance(Employee.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (Employee) unmarshaller.unmarshal(new StringReader(xml));

        } catch (Exception e) {
            throw new BadRequestException("Invalid XML format");
        }
    }
}