package org.mail.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: if no reply to oracle aq delete me
public class JmsMessage {
    private static final Logger log = LoggerFactory.getLogger(JmsMessage.class);

    private String replyTo;
    private int timeToLive;
    private boolean persistent;
    private String messagePayload;

    public JmsMessage() {
    }

    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    public int getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(int timeToLive) {
        this.timeToLive = timeToLive;
    }

    public boolean isPersistent() {
        return persistent;
    }

    public void setPersistent(boolean persistent) {
        this.persistent = persistent;
    }

    public String getMessagePayload() {
        return messagePayload;
    }

    public void setMessagePayload(String messagePayload) {
        this.messagePayload = messagePayload;
    }

}