package com.odeyalo.analog.netflix;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.test.context.EmbeddedKafka;

@SpringBootTest("spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration")
@EmbeddedKafka(brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
@Import(MockFFmpegConfiguration.class)
public class StorageApplicationTest {

    @Test
    void contextLoads() {
        System.out.println("Context has been started");
    }
}
