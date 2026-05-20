//package com.ems.service;
//import com.ems.entity.*;
//import com.ems.messaging.EmployeeProducer;
//import com.ems.repository.*;
//import com.ems.validation.XMLValidator;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import jakarta.xml.bind.JAXBContext;
//import jakarta.xml.bind.Unmarshaller;
//
//import java.io.StringReader;
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Service
//public class EmployeeServiceImpl implements EmployeeService {
//
//    @Autowired
//    private EmployeeRepository repo;
//
//    @Autowired
//    private AuditLogRepository auditRepo;
//
//    @Autowired
//    private EmployeeProducer producer;
//
//    @Autowired
//    private XMLValidator validator;
//
//    @Override
//    public Employee create(String xml) {
//        validator.validate(xml);
//
//        Employee emp = convertXMLToObject(xml);
//        Employee saved = repo.save(emp);
//
//        sendAndLog(saved.getId(), "CREATE");
//        return saved;
//    }
//
//    @Override
//    public Employee update(Long id, Employee emp) {
//        emp.setId(id);
//        Employee updated = repo.save(emp);
//
//        sendAndLog(id, "UPDATE");
//        return updated;
//    }
//
//    @Override
//    public void delete(Long id) {
//        repo.deleteById(id);
//        sendAndLog(id, "DELETE");
//    }
//
//    @Override
//    public Employee getById(Long id) {
//        return repo.findById(id).orElseThrow();
//    }
//
//    @Override
//    public List<Employee> getAll() {
//        return repo.findAll();
//    }
//
//    private Employee convertXMLToObject(String xml) {
//        try {
//            JAXBContext context = JAXBContext.newInstance(Employee.class);
//            Unmarshaller unmarshaller = context.createUnmarshaller();
//            return (Employee) unmarshaller.unmarshal(new StringReader(xml));
//        } catch (Exception e) {
//            throw new RuntimeException("XML Parsing Error");
//        }
//    }
//    private void sendAndLog(Long id, String action) {
//        AuditLog log = new AuditLog();
//        log.setEmployeeId(id);
//        log.setAction(action);
//        log.setSource("REST");
//        log.setTimestamp(LocalDateTime.now());
//
//        auditRepo.save(log);
//
//
//        producer.sendEvent(id, action);
//    }
//}

package com.ems.service;

import com.ems.entity.*;
import com.ems.messaging.EmployeeProducer;
import com.ems.repository.*;
import org.springframework.stereotype.Service;

import jakarta.xml.bind.*;

import java.io.StringReader;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repo;
    private final AuditLogRepository auditRepo;
    private final EmployeeProducer producer;

    public EmployeeServiceImpl(EmployeeRepository repo,
                               AuditLogRepository auditRepo,
                               EmployeeProducer producer) {
        this.repo = repo;
        this.auditRepo = auditRepo;
        this.producer = producer;
    }

    public Employee create(String xml) {
        Employee emp = convertXMLToObject(xml);
        Employee saved = repo.save(emp);

        sendAndLog(saved.getId(), "CREATE");
        return saved;
    }

    public Employee update(Long id, Employee emp) {
        emp.setId(id);
        Employee updated = repo.save(emp);

        sendAndLog(id, "UPDATE");
        return updated;
    }

    public void delete(Long id) {
        repo.deleteById(id);
        sendAndLog(id, "DELETE");
    }

    public Employee getById(Long id) {
        return repo.findById(id).orElseThrow();
    }

    public List<Employee> getAll() {
        return repo.findAll();
    }

    private Employee convertXMLToObject(String xml) {
        try {
            JAXBContext context = JAXBContext.newInstance(Employee.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (Employee) unmarshaller.unmarshal(new StringReader(xml));
        } catch (Exception e) {
            throw new RuntimeException("XML parsing failed");
        }
    }

    private void sendAndLog(Long id, String action) {

        // ✅ REST FIRST
        AuditLog log = new AuditLog();
        log.setEmployeeId(id);
        log.setAction(action);
        log.setSource("REST");
        log.setTimestamp(LocalDateTime.now());

        auditRepo.save(log);

        // ✅ JMS AFTER
        producer.sendEvent(id, action);
    }
}