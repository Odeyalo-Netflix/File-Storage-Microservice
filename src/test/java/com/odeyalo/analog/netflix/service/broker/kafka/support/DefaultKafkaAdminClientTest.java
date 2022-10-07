package com.odeyalo.analog.netflix.service.broker.kafka.support;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;


class DefaultKafkaAdminClientTest {
    private final DefaultKafkaAdminClient client = new DefaultKafkaAdminClient("localhost:9200");

    @Test
    void verifyConnection() {
        boolean connection = client.verifyConnection();
        assertTrue(connection);
    }
}
