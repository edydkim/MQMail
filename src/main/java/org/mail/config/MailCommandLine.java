package org.mail.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailCommandLine {
    private static final Logger log = LoggerFactory.getLogger(MailCommandLine.class);

    private boolean help;
    private boolean startup;
    private boolean isTestMode;
    private long hookingInterval;

    public boolean isStartup() {
        return startup;
    }

    public void setStartup(boolean startup) {
        this.startup = startup;
    }

    public boolean isTestMode() {
        return isTestMode;
    }

    public void setIsTestMode(boolean isTestMode) {
        this.isTestMode = isTestMode;
    }

    public boolean isHelp() {
        return help;
    }

    public void setHelp(boolean help) {
        this.help = help;
    }

    public long getHookingInterval() {
        return hookingInterval;
    }

    public void setHookingInterval(long hookingInterval) {
        this.hookingInterval = hookingInterval;
    }
}
