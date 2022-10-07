package com.odeyalo.analog.netflix.service.broker.kafka.support;

import lombok.*;
import lombok.experimental.Accessors;


/**
 * Represent the current state of Apache kafka. Contains info about broker
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KafkaState {
    /**
     * True if kafka is now available
     */
    @Getter
    @Accessors(fluent = true)
    private boolean status;
}
