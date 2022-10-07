package com.odeyalo.analog.netflix.service.broker.kafka;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.event.NonResponsiveConsumerEvent;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class KafkaExceptionKafkaMessageListener {

    @EventListener()
    public void handleException(NonResponsiveConsumerEvent event) {
        log.info("Event received: {}", event);
    }
}
