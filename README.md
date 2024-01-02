## MQMail
Multi Queuing Mail using AQ, JMS and so on..

see in detail below..

## SDK
JDK 11+

## Framework
Spring 5x

Apache Thrift

## Extension
JMX

JMS

Activemq

Oracle AQ (Deprecated, but to be manually installed in $ORACLE_HOME/rdbms/jlib/aqapi.jar)

Thymeleaf

## Build
Maven

## Mail servers tested
sendmail..

## Run
$>java -Dfile.encoding=UTF-8 -classpath ...jar org.mail.client.CliMain -i 2000

## Queue
CASE 1. Oracle Streams Advanced Queuing (Deprecated, but to be manually installed in $ORACLE_HOME/rdbms/jlib/aqapi.jar)

Procedure (PL/SQL):
```
CREATE OR REPLACE TYPE USER_DEFINED_TYPE AS OBJECT (
    global_user_id NUMBER(7),
    mail_info_type NUMBER(2),
    datetime TIMESTAMP
);
/
BEGIN DBMS_AQADM.CREATE_QUEUE_TABLE(
    Queue_table        => 'QUEUE_TABLE',
    Queue_payload_type => 'USER_DEFINED_TYPE',
    storage_clause     => 'PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 TABLESPACE USERS',
    Sort_list          => 'ENQ_TIME',
    Compatible         => '8.1.3');
END;
/
BEGIN DBMS_AQADM.CREATE_QUEUE(
    Queue_name          => 'QUEUE',
    Queue_table         => 'QUEUE_TABLE',
    Queue_type          =>  0,
    Max_retries         =>  5,
    Retry_delay         =>  0,
    dependency_tracking =>  FALSE);
END;
/
BEGIN
	dbms_aqadm.start_queue (queue_name => 'QUEUE', enqueue => TRUE , dequeue => TRUE);
END;
```

In this case, you need to install DBMS with a driver, jdbc - Oracle xe.

## ThreadSafe
JmxMain
