

package com.ems.controller;

import com.ems.dto.EmployeeRequestDTO;
import com.ems.dto.EmployeeResponseDTO;
import com.ems.dto.ResponseStructure;
import com.ems.service.EmployeeService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmployeeControllerTest {

    @Mock
    private EmployeeService service;

    @InjectMocks
    private EmployeeController controller;

    private EmployeeRequestDTO requestDTO;
    private EmployeeResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        requestDTO = new EmployeeRequestDTO();
        requestDTO.setName("Test");
        requestDTO.setEmail("test@gmail.com");
        requestDTO.setDepartment("IT");
        requestDTO.setDateOfJoining("2025-01-01");
        requestDTO.setPhone("9876543210");
        requestDTO.setSalary(50000.0);
        requestDTO.setStatus("ACTIVE");

        responseDTO = new EmployeeResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setName("Test");
        responseDTO.setEmail("test@gmail.com");
        responseDTO.setDepartment("IT");
        responseDTO.setDateOfJoining("2025-01-01");
        responseDTO.setPhone("9876543210");
        responseDTO.setSalary(50000.0);
        responseDTO.setStatus("ACTIVE");
    }

    // ✅ CREATE TEST (XML)
    @Test
    void testCreate_success() {

        String xml = "<Employee></Employee>";

        when(service.create(xml)).thenReturn(responseDTO);

        ResponseEntity<ResponseStructure<EmployeeResponseDTO>> response =
                controller.create(xml);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        ResponseStructure<EmployeeResponseDTO> body = response.getBody();

        assertEquals("success", body.getStatus());
        assertEquals("Employee created successfully", body.getMessage());
        assertEquals(responseDTO, body.getData());

        verify(service, times(1)).create(xml);
    }


    // ✅ UPDATE TEST
    @Test
    void testUpdate_success() {

        when(service.update(1L, requestDTO)).thenReturn(responseDTO);

        ResponseEntity<ResponseStructure<EmployeeResponseDTO>> response =
                controller.update(1L, requestDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        ResponseStructure<EmployeeResponseDTO> body = response.getBody();

        assertEquals("success", body.getStatus());
        assertEquals("Employee updated successfully", body.getMessage());
        assertEquals(responseDTO, body.getData());

        verify(service, times(1)).update(1L, requestDTO);
    }


    // ✅ DELETE TEST
    @Test
    void testDelete_success() {

        doNothing().when(service).delete(1L);

        ResponseEntity<ResponseStructure<String>> response =
                controller.delete(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        ResponseStructure<String> body = response.getBody();

        assertEquals("success", body.getStatus());
        assertEquals("Employee deleted successfully", body.getMessage());
        assertNull(body.getData());

        verify(service, times(1)).delete(1L);
    }


    // ✅ GET BY ID TEST
    @Test
    void testGetById_success() {

        when(service.getById(1L)).thenReturn(responseDTO);

        ResponseEntity<ResponseStructure<EmployeeResponseDTO>> response =
                controller.getById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        ResponseStructure<EmployeeResponseDTO> body = response.getBody();

        assertEquals("success", body.getStatus());
        assertEquals("Employee fetched successfully", body.getMessage());
        assertEquals(responseDTO, body.getData());

        verify(service, times(1)).getById(1L);
    }


    // ✅ GET ALL TEST
    @Test
    void testGetAll_success() {

        List<EmployeeResponseDTO> list = List.of(responseDTO);

        when(service.getAll()).thenReturn(list);

        ResponseEntity<ResponseStructure<List<EmployeeResponseDTO>>> response =
                controller.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());

        ResponseStructure<List<EmployeeResponseDTO>> body = response.getBody();

        assertEquals("success", body.getStatus());
        assertEquals("Employee list fetched", body.getMessage());
        assertEquals(list, body.getData());

        verify(service, times(1)).getAll();
    }
}

