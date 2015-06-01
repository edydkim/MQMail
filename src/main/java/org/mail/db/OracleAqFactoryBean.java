package org.mail.db;


import oracle.jms.AQjmsFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import javax.jms.ConnectionFactory;
import javax.sql.DataSource;


@Component
public class OracleAqFactoryBean implements FactoryBean {
    private static final Logger log = LoggerFactory.getLogger(OracleAqFactoryBean.class);

    private DataSource defaultDataSource;

    public void setDefaultDataSource(DataSource defaultDataSource) {
        this.defaultDataSource = defaultDataSource;
    }

    @Override
    public Object getObject() throws Exception {
        return AQjmsFactory.getQueueConnectionFactory(defaultDataSource);
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