package com.odeyalo.analog.netflix.service.broker.kafka.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * KafkaStateUpdater implementation that use scheduling to update current broker status
 */
@Component
public class ScheduledKafkaStateUpdater implements KafkaStateUpdater {
    private final KafkaAdminClient client;
    private final Logger logger = LoggerFactory.getLogger(ScheduledKafkaStateUpdater.class);

    @Autowired
    public ScheduledKafkaStateUpdater(KafkaAdminClient client) {
        this.client = client;
    }

    @Override
    @Scheduled(fixedDelayString = "${app.scheduler.kafka.delay.ms}")
    public void updateKafkaState() {
        boolean connectionStatus = client.verifyConnection();
        this.logger.info("Update current status from: {} to {}", KafkaStateHolder.getState().status(), connectionStatus);
        KafkaStateHolder.getState().status(connectionStatus);
    }
}
