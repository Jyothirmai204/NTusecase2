package com.ems.messaging;

import com.ems.entity.AuditLog;
import com.ems.repository.AuditLogRepository;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EmployeeConsumer {

    private final AuditLogRepository auditRepo;

    public EmployeeConsumer(AuditLogRepository auditRepo) {
        this.auditRepo = auditRepo;
    }

    @JmsListener(destination = "employee.events")
    public void receive(String msg) {

        System.out.println("Received: " + msg);

        // ✅ Extract employee ID from message
        Long employeeId = extractId(msg);

        // ✅ Save audit log with ID
        AuditLog log = new AuditLog();
        log.setEmployeeId(employeeId);   // ✅ FIX
        log.setAction("MESSAGE_RECEIVED");
        log.setSource("JMS");
        log.setTimestamp(LocalDateTime.now());

        auditRepo.save(log);
    }

    // ✅ Helper method to extract ID
    private Long extractId(String msg) {
        try {
            // Example message: "Employee 1 CREATE"
            String[] parts = msg.split(" ");
            return Long.parseLong(parts[1]);
        } catch (Exception e) {
            return null;
        }
    }
}