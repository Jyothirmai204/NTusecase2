package com.ems.messaging;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class EmployeeProducer {

    private final JmsTemplate jmsTemplate;

    public EmployeeProducer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;  //constructoor injection
    }

    public void sendEvent(Long id, String action) {
        String msg = "Employee " + id + " " + action;
        jmsTemplate.convertAndSend("employee.events", msg);
    }
}
