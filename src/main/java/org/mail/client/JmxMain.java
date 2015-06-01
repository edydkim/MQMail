package org.mail.client;

import org.mail.jmx.MBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.concurrent.CountDownLatch;


@Component
public class JmxMain {
    private static final Logger logger = LoggerFactory.getLogger(JmxMain.class);
    private CountDownLatch latch;

    private MBean mBean;

    public void setmBean(MBean mBean) {
        this.mBean = mBean;
    }

    public static final void main(String[] args) {
        try {
            new ClassPathXmlApplicationContext("mail-context.xml")
                    .getBean(JmxMain.class)
                    .start(args);
            System.exit(0);
        }
        catch (Throwable th) {
            logger.error("Exception occurred.", th);
            System.exit(1);
        }
    }

    public void start(String... args) throws Exception {
        logger.info("starting mail process.");
        long start = System.currentTimeMillis();
        try {
            registerMBean();
        }
        catch (Exception e) {
            throw new IllegalStateException("Exception occurred during starting mail process.", e);
        }
        finally {
            logger.info("started mail process. time[" + (System.currentTimeMillis() - start) + "]");
            latch = new CountDownLatch(1);
        }
        latch.await();
    }

    public void stop() {
        if (latch == null) {
            return;
        }
        latch.countDown();
    }

    private void registerMBean() throws Exception {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("bean:name=Mail");
        if (!mbs.isRegistered(name)) {
            mbs.registerMBean(mBean, name);
        }
    }
}
