package org.mail.jmx;

import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class Browser {

    public enum MethodType {
        ST
    }

    private static final String USAGE =  "Usage:Browser jmxAddress:port method [param]¥n"
            + "¥tjmxAddress:port¥t:address:port¥n"
            + "¥tmethod¥t¥t:select type[ST]¥n"
            + "¥t¥t¥t¥t:ST -- stop()¥n"
            ;
    /**
     * @param args
     */
    public static void main(String[] args) {
        if (args == null || (args.length != 2 && args.length != 3)) {
            System.err.println(USAGE);
            System.exit(1);
            return;
        }
        try {
            JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://" + args[0] + "/jmxrmi");
            JMXConnector connector = JMXConnectorFactory.connect(url);
            MBeanServerConnection conn = connector.getMBeanServerConnection();
            ObjectName name = new ObjectName("bean:name=Mail");
            MBean client = MBeanServerInvocationHandler.newProxyInstance(conn, name, MBean.class, true);

            MethodType method = MethodType.valueOf(args[1]);
            String param = null;
            if (args.length == 3) {
                param = args[2];
            }
            String ret = org.apache.commons.lang.StringUtils.EMPTY;

            switch (method) {
                case ST:
                    client.stop();
                    ret = "stopped.";
                    break;
                default :
                    System.err.println(USAGE);
                    System.exit(1);
                    return;
            }
            System.out.println(ret);
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
