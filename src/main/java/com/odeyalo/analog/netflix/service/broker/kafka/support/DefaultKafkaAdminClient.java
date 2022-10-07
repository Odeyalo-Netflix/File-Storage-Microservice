package com.odeyalo.analog.netflix.service.broker.kafka.support;

import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.common.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Properties;

public class DefaultKafkaAdminClient implements KafkaAdminClient {
    private final Properties properties;
    private final Admin client;
    private final Logger logger = LoggerFactory.getLogger(DefaultKafkaAdminClient.class);

    public DefaultKafkaAdminClient(String bootstrap) {
        this.properties = new Properties();
        properties.put("bootstrap.servers", bootstrap);
        properties.put(AdminClientConfig.RETRY_BACKOFF_MS_CONFIG, 7000);
        properties.put(AdminClientConfig.REQUEST_TIMEOUT_MS_CONFIG, 5000);
        properties.put(AdminClientConfig.DEFAULT_API_TIMEOUT_MS_CONFIG, 5000);
        properties.put(AdminClientConfig.RECONNECT_BACKOFF_MS_CONFIG, 10000);
        properties.put("connections.max.idle.ms", 5000);
        this.client = Admin.create(properties);
    }

    public DefaultKafkaAdminClient(Properties properties) {
        this.properties = properties;
        this.client = Admin.create(properties);
    }

    @Override
    public boolean verifyConnection() {
        try {
            Collection<Node> nodes = this.client.describeCluster()
                    .nodes()
                    .get();
            return nodes != null && nodes.size() > 0;
        } catch (Exception ex) {
            this.logger.error("Failed to verify connection to kafka broker.", ex);
            return false;
        }
    }

    @Override
    public Admin getOriginalKafkaAdmin() {
        return client;
    }

    @Override
    public Properties getAdminProperties() {
        return properties;
    }
}
