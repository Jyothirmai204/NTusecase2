package com.ems.messaging;

import com.ems.entity.AuditLog;
import com.ems.repository.AuditLogRepository;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EmployeeConsumer {

    private final AuditLogRepository repo;

    public EmployeeConsumer(AuditLogRepository repo) {
        this.repo = repo;
    }

    @JmsListener(destination = "employee.events")
    public void receive(String msg) {

        Long id = Long.parseLong(msg.split(" ")[1]);

        AuditLog log = new AuditLog();
        log.setEmployeeId(id);
        log.setAction("MESSAGE_RECEIVED");
        log.setSource("JMS");
        log.setTimestamp(LocalDateTime.now());

        repo.save(log);
    }
}