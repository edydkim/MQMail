package org.mail.jmx;

import org.mail.client.JmxMain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MBeanImpl implements MBean {

    @Autowired
    private JmxMain jmxMain;

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        jmxMain.stop();
    }
}
