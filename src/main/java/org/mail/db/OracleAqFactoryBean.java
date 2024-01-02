package org.mail.db;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.jms.ConnectionFactory;
import javax.sql.DataSource;


@Component
public class OracleAqFactoryBean implements FactoryBean {
    private static final Logger log = LoggerFactory.getLogger(OracleAqFactoryBean.class);

    private DataSource defaultDataSource;

    @Value("${active.mq.broker-url}")
    private String brokerUrl;

    public void setDefaultDataSource(DataSource defaultDataSource) {
        this.defaultDataSource = defaultDataSource;
    }

    @Override
    public Object getObject() throws Exception {
        // NOTE: if you don't use to session Message Listener for oracle aq, delete me
        // return AQjmsFactory.getQueueConnectionFactory(defaultDataSource);
        return new ActiveMQConnectionFactory(brokerUrl);
    }

    @Override
    public Class<?> getObjectType() {
        return ConnectionFactory.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
