package org.mail.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by edydkim on 2015/06.
 */
@Component
public class Scheduler {
    private static final Logger log = LoggerFactory.getLogger(Scheduler.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("YY/MM/DD HH:mm:ss");

    @Scheduled
    public void reportCurrentTime() {
        log.info("Heart Beat's Checked: " + dateFormat.format(new Date()));
        log.debug("I'm alive, waiting queue..");
    }
}
