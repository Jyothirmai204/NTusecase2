//package com.ems.controller;
//
//import com.ems.entity.Employee;
//import com.ems.service.EmployeeService;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/employees")
//public class EmployeeController {
//
//    private final EmployeeService service;
//
//    public EmployeeController(EmployeeService service) {
//        this.service = service;
//    }
//
//    @PostMapping(consumes = "application/xml")
//    public Employee create(@RequestBody String xml) {
//        return service.create(xml);
//    }
//
//    @PutMapping("/{id}")
//    public Employee update(@PathVariable Long id, @RequestBody Employee emp) {
//        return service.update(id, emp);
//    }
//
//    @DeleteMapping("/{id}")
//    public String delete(@PathVariable Long id) {
//        service.delete(id);
//        return "Deleted successfully";
//    }
//
//    @GetMapping
//    public List<Employee> getAll() {
//        return service.getAll();
//    }
//}

package com.ems.controller;

import com.ems.dto.*;
import com.ems.service.EmployeeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService service;

    // ✅ CREATE (XML input)
    @PostMapping(consumes = "application/xml")
    public ResponseEntity<ResponseStructure<EmployeeResponseDTO>> create(
            @RequestBody String xml) {

        EmployeeResponseDTO data = service.create(xml);

        ResponseStructure<EmployeeResponseDTO> response = new ResponseStructure<>(
                "success",
                "Employee created successfully",
                data
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ✅ UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<ResponseStructure<EmployeeResponseDTO>> update(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRequestDTO dto) {

        EmployeeResponseDTO data = service.update(id, dto);

        ResponseStructure<EmployeeResponseDTO> response = new ResponseStructure<>(
                "success",
                "Employee updated successfully",
                data
        );

        return ResponseEntity.ok(response);
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseStructure<String>> delete(
            @PathVariable Long id) {

        service.delete(id);

        ResponseStructure<String> response = new ResponseStructure<>(
                "success",
                "Employee deleted successfully",
                null
        );

        return ResponseEntity.ok(response);
    }

    // ✅ GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ResponseStructure<EmployeeResponseDTO>> getById(
            @PathVariable Long id) {

        EmployeeResponseDTO data = service.getById(id);

        ResponseStructure<EmployeeResponseDTO> response = new ResponseStructure<>(
                "success",
                "Employee fetched successfully",
                data
        );

        return ResponseEntity.ok(response);
    }

    // ✅ GET ALL
    @GetMapping
    public ResponseEntity<ResponseStructure<List<EmployeeResponseDTO>>> getAll() {

        List<EmployeeResponseDTO> data = service.getAll();

        ResponseStructure<List<EmployeeResponseDTO>> response =
                new ResponseStructure<>(
                        "success",
                        "Employee list fetched",
                        data
                );

        return ResponseEntity.ok(response);
    }
}
