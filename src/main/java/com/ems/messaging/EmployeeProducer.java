//package com.ems.messaging;
//
//import org.springframework.jms.core.JmsTemplate;
//import org.springframework.stereotype.Component;
//import org.springframework.beans.factory.annotation.Autowired;
//
//@Component
//public class EmployeeProducer {
//
//    @Autowired
//    private JmsTemplate jmsTemplate;
//
//    public void sendEvent(Long id, String action) {
//        String msg = "Employee " + id + " " + action;
//        jmsTemplate.convertAndSend("employee.events", msg);
//    }
//}

package com.ems.messaging;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class EmployeeProducer {

    private final JmsTemplate jmsTemplate;

    public EmployeeProducer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendEvent(Long id, String action) {
        String msg = "Employee " + id + " " + action;
        jmsTemplate.convertAndSend("employee.events", msg);
    }
}
