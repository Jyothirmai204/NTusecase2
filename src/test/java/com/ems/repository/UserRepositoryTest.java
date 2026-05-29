package com.ems.repository;

import com.ems.entity.User;
import com.ems.Main;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = Main.class)
class UserRepositoryTest {

    @Autowired
    private UserRepository repo;

    @Test
    void testFindByEmail() {

        User user = new User();

        user.setName("Test User");
        user.setPhone("9876543210");
        user.setEmail("test@gmail.com");

        // ✅ FIXED PASSWORD (6+ characters)
        user.setPassword("123456");

        repo.save(user);

        User result = repo.findByEmail("test@gmail.com");

        assertNotNull(result);
        assertEquals("test@gmail.com", result.getEmail());
    }
}