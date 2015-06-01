package org.mail.jmx;

import javax.management.MXBean;

@MXBean
public interface MBean {

    // stop a process
    void stop();
}
