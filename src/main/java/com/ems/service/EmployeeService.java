//package com.ems.service;
//
//import com.ems.entity.Employee;
//import java.util.List;
//
//public interface EmployeeService {
//
//    Employee create(String xml);
//    Employee update(Long id, Employee emp);
//    void delete(Long id);
//    Employee getById(Long id);
//    List<Employee> getAll();
//}
package com.ems.service;

import com.ems.dto.EmployeeRequestDTO;
import com.ems.dto.EmployeeResponseDTO;

import java.util.List;

public interface EmployeeService {

    EmployeeResponseDTO create(String xml);

    EmployeeResponseDTO update(Long id, EmployeeRequestDTO dto);

    void delete(Long id);

    EmployeeResponseDTO getById(Long id);

    List<EmployeeResponseDTO> getAll();
}
