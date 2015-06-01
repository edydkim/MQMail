package org.mail.listener;


import org.mail.db.dto.MailInfoParamDto;
import org.mail.domain.JmsMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.sql.DataSource;

// TODO: if you don"t use to session Message Listener for oracle aq, delete me
@Component
public class SessionMessageListener implements SessionAwareMessageListener {
    private static final Logger log = LoggerFactory.getLogger(SessionMessageListener.class);

    private DataSource defaultDataSource;

    public void setDefaultDataSource(DataSource defaultDataSource) {
        this.defaultDataSource = defaultDataSource;
    }

    private JmsTemplate jmsTemplate;

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    private final JmsMessage jmsMessage = new JmsMessage();

    @Override
    public void onMessage(Message message, Session session) throws JMSException {
        MailInfoParamDto mailInfoParamDto = (MailInfoParamDto) message;

        log.debug("Received message with content:" + mailInfoParamDto.toString());

        jmsTemplate.send(otherSession -> {
            Message otherMessage = otherSession.createObjectMessage();
            return otherMessage;
        });

        log.debug("Sent message to Q2");

        if (jmsMessage.isPersistent()) {
            jmsTemplate.setDeliveryPersistent(jmsMessage.isPersistent());
        }

        if (0 != jmsMessage.getTimeToLive()) {
            jmsTemplate.setTimeToLive(jmsMessage.getTimeToLive());
        }

        jmsTemplate.send(otherSession -> {
            Message otherMessage = otherSession.createObjectMessage();
            return otherMessage;
        });
    }
}
