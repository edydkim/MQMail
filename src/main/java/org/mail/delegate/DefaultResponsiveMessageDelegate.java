package org.mail.delegate;


import org.mail.db.dto.MailInfoParamDto;
import org.mail.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.transaction.Transactional;


// FIXME: define the spec
@Component
public class DefaultResponsiveMessageDelegate implements ResponsiveMessageDelegate {
    private static final Logger log = LoggerFactory.getLogger(DefaultResponsiveMessageDelegate.class);

    private MailService mailService;

    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    // THIS METHOD IS TRANSACTIONAL
    // RETRYED 3 TIMES
    // IF ANY ERROR OCCURRED A ITEM IN THE QUEUE NOT CONSUMED
    // TODO: to refactor this method - Gof, Command Pattern
    @Transactional
    @Override
    public String receive(MailInfoParamDto message) {
        log.info("ready..");

        String whichMailToSend = "default";

        if (message == null) throw new NullPointerException("message is null..");
        log.debug(message.toString() + " is extracted..");

        try {
            mailService.send(whichMailToSend);
        } catch (MessagingException e) {
            log.error("Sending mail is failed..: ", e);
        }

        // TODO: to persist mailInfoParamDto for a Database.

        log.info("consumed..");

        // nothing to reply a message
        return null;
    }
}
