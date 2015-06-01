package org.mail.service;


import javax.mail.MessagingException;


public interface MailService {

    void send(String whichMailToSend) throws MessagingException;
}
