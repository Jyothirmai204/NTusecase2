package com.ems.repository;

import com.ems.entity.AuditLog;
import com.ems.Main;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = Main.class)
class AuditLogRepositoryTest {

    @Autowired
    private AuditLogRepository repo;


    @Test
    void testSaveAuditLog() {

        AuditLog log = new AuditLog();
        log.setEmployeeId(1L);
        log.setAction("CREATE");
        log.setSource("REST");
        log.setTimestamp(LocalDateTime.now());

        AuditLog saved = repo.save(log);

        assertNotNull(saved.getId());
        assertEquals("CREATE", saved.getAction());
    }


    @Test
    void testFindAllAuditLogs() {

        AuditLog log1 = new AuditLog();
        log1.setEmployeeId(1L);
        log1.setAction("CREATE");
        log1.setSource("REST");
        log1.setTimestamp(LocalDateTime.now());

        AuditLog log2 = new AuditLog();
        log2.setEmployeeId(2L);
        log2.setAction("DELETE");
        log2.setSource("REST");
        log2.setTimestamp(LocalDateTime.now());

        repo.save(log1);
        repo.save(log2);

        List<AuditLog> logs = repo.findAll();

        assertEquals(2, logs.size());
    }
}