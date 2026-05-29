package com.ems.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class XMLValidatorTest {

    private final XMLValidator validator = new XMLValidator();

    // ✅ VALID XML
    @Test
    void testValidXML() {

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

        assertDoesNotThrow(() -> validator.validate(xml));
    }

    // ✅ INVALID XML
    @Test
    void testInvalidXML() {

        String invalidXml = "<Employee><name>John</name>";

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> validator.validate(invalidXml));

        assertTrue(ex.getMessage().contains("Invalid XML"));
    }
}
