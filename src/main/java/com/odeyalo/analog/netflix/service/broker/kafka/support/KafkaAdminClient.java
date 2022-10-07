package com.odeyalo.analog.netflix.service.broker.kafka.support;

import org.apache.kafka.clients.admin.Admin;

import java.util.Properties;

public interface KafkaAdminClient {

    boolean verifyConnection();

    Admin getOriginalKafkaAdmin();

    Properties getAdminProperties();
}
