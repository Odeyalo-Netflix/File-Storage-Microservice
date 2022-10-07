package com.odeyalo.analog.netflix.service.broker.kafka.compressor;

import com.odeyalo.analog.netflix.service.broker.kafka.KafkaMessageListener;
import com.odeyalo.analog.netflix.service.image.size.compressor.SizeCompressorResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Component
public class ByteImageCompressorKafkaMessageListener implements KafkaMessageListener<byte[]> {
    private final SizeCompressorResolver sizeCompressorResolver;
    private final Logger logger = LoggerFactory.getLogger(ByteImageCompressorKafkaMessageListener.class);
    private static final String BYTE_IMAGE_COMPRESSOR_KAFKA_TOPIC = "BYTE_IMAGE_COMPRESSOR_TOPIC";

    public ByteImageCompressorKafkaMessageListener(SizeCompressorResolver sizeCompressorResolver) {
        this.sizeCompressorResolver = sizeCompressorResolver;
    }

    @Override
    @KafkaListener(topics = BYTE_IMAGE_COMPRESSOR_KAFKA_TOPIC, containerFactory = "bytesConsumerFactory")
    public void receiveMessage(byte[] message) throws IOException {
        String path = this.sizeCompressorResolver.compress(new ByteArrayInputStream(message));
        this.logger.info("CREATED AND COMPRESSED FILE. SAVED FILE TO PATH {}", path);
    }
}
