package com.ems.service;

import com.ems.dto.EmployeeRequestDTO;
import com.ems.dto.EmployeeResponseDTO;
import com.ems.entity.Employee;
import com.ems.entity.AuditLog;
import com.ems.exception.BadRequestException;
import com.ems.mapper.EmployeeMapper;
import com.ems.messaging.EmployeeProducer;
import com.ems.repository.AuditLogRepository;
import com.ems.repository.EmployeeRepository;
import com.ems.validation.XMLValidator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository repo;

    @Mock
    private AuditLogRepository auditRepo;

    @Mock
    private EmployeeProducer producer;

    @Mock
    private EmployeeMapper mapper;

    @Mock
    private XMLValidator xmlValidator;

    @InjectMocks
    private EmployeeServiceImpl service;

    // ✅ CREATE SUCCESS
    @Test
    void testCreate_success() {

        String xml = """
                <Employee>
                    <name>John</name>
                    <email>john@gmail.com</email>
                    <department>IT</department>
                    <dateOfJoining>2024-01-01</dateOfJoining>
                    <phone>9876543210</phone>
                    <salary>50000</salary>
                    <status>ACTIVE</status>
                </Employee>
                """;

        Employee saved = new Employee();
        saved.setId(1L);
        saved.setName("John");
        saved.setSalary(50000.0);
        saved.setStatus("ACTIVE");

        EmployeeResponseDTO dto = new EmployeeResponseDTO();
        dto.setName("John");

        doNothing().when(xmlValidator).validate(xml);

        when(repo.save(any(Employee.class))).thenReturn(saved);
        when(mapper.toDTO(saved)).thenReturn(dto);

        EmployeeResponseDTO result = service.create(xml);

        assertNotNull(result);
        verify(repo).save(any(Employee.class));
        verify(producer).sendEvent(1L, "CREATE");
    }

    // ✅ CREATE INVALID SALARY
    @Test
    void testCreate_invalidSalary() {

        String xml = """
                <Employee>
                    <salary>0</salary>
                    <status>ACTIVE</status>
                </Employee>
                """;

        doNothing().when(xmlValidator).validate(xml);

        assertThrows(BadRequestException.class,
                () -> service.create(xml));
    }

    // ✅ UPDATE SUCCESS
    @Test
    void testUpdate_success() {

        Employee existing = new Employee();
        existing.setId(1L);

        EmployeeRequestDTO dto = new EmployeeRequestDTO();
        dto.setSalary(50000.0);
        dto.setStatus("ACTIVE");

        EmployeeResponseDTO responseDTO = new EmployeeResponseDTO();

        when(repo.findById(1L)).thenReturn(Optional.of(existing));
        when(repo.save(existing)).thenReturn(existing);
        when(mapper.toDTO(existing)).thenReturn(responseDTO);

        EmployeeResponseDTO result = service.update(1L, dto);

        assertNotNull(result);

        verify(repo).save(existing);
        verify(producer).sendEvent(1L, "UPDATE");
    }

    // ✅ DELETE SUCCESS
    @Test
    void testDelete_success() {

        when(repo.existsById(1L)).thenReturn(true);

        service.delete(1L);

        verify(repo).deleteById(1L);
        verify(producer).sendEvent(1L, "DELETE");
    }

    // ✅ DELETE NOT FOUND
    @Test
    void testDelete_notFound() {

        when(repo.existsById(1L)).thenReturn(false);

        assertThrows(RuntimeException.class,
                () -> service.delete(1L));
    }

    // ✅ GET BY ID
    @Test
    void testGetById_success() {

        Employee emp = new Employee();
        emp.setId(1L);

        EmployeeResponseDTO dto = new EmployeeResponseDTO();

        when(repo.findById(1L)).thenReturn(Optional.of(emp));
        when(mapper.toDTO(emp)).thenReturn(dto);

        EmployeeResponseDTO result = service.getById(1L);

        assertNotNull(result);
    }

    // ✅ GET ALL
    @Test
    void testGetAll_success() {

        Employee emp = new Employee();
        EmployeeResponseDTO dto = new EmployeeResponseDTO();

        when(repo.findAll()).thenReturn(List.of(emp));
        when(mapper.toDTO(emp)).thenReturn(dto);

        List<EmployeeResponseDTO> list = service.getAll();

        assertEquals(1, list.size());
    }
}
