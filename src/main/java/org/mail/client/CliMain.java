package org.mail.client;

import org.mail.config.MailCommandLine;
import org.mail.config.MailCommandLineParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
public class CliMain {
    private static final Logger log = LoggerFactory.getLogger(CliMain.class);

    private static MailCommandLine commandLine = new MailCommandLine();

    public static void main(String[] args) throws Exception {
        // create Spring context
        ApplicationContext ctx = new ClassPathXmlApplicationContext("mail-context.xml");

        MailCommandLineParser parser = new MailCommandLineParser();
        try {
            commandLine = parser.parse(args);
            if (!commandLine.isHelp()) {
                new CliMain().start();
                return;
            } else {
                parser.help();
            }
        } catch (Exception ex) {
            log.error("Caught exception.", ex);
        }
        Runtime.getRuntime().exit(1);
    }

    public void start() throws Exception {
        log.info("Started..");

        while (true) {
            try {
                // Quietly
                Thread.sleep(commandLine.getHookingInterval());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
