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

        AuditLog log = new AuditLog();
        log.setAction("MESSAGE_RECEIVED");
        log.setSource("JMS");
        log.setTimestamp(LocalDateTime.now());

        auditRepo.save(log);

        System.out.println("Received: " + msg);
    }
}