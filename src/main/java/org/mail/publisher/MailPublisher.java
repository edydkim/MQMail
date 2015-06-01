package org.mail.publisher;

import org.mail.domain.JmsMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;


@Component
public class MailPublisher {
    private static final Logger log = LoggerFactory.getLogger(MailPublisher.class);

    private JmsTemplate jmsTemplate;

    public void sendMessage(final JmsMessage jmsMessage) throws JMSException {

        if (jmsMessage.isPersistent()) {
            jmsTemplate.setDeliveryPersistent(jmsMessage.isPersistent());
        }

        if (0 != jmsMessage.getTimeToLive()) {
            jmsTemplate.setTimeToLive(jmsMessage.getTimeToLive());
        }

        jmsTemplate.send(session -> {
            TextMessage message = session.createTextMessage(jmsMessage.getMessagePayload());
            message.setLongProperty("startTime", System.currentTimeMillis());
            return message;
        });
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }
}
