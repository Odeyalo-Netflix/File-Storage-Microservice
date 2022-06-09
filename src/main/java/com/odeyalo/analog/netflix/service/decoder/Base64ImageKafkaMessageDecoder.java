package com.odeyalo.analog.netflix.service.decoder;

import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;

@Component
public class Base64ImageKafkaMessageDecoder implements KafkaMessageDecoder<InputStream, String> {

    @Override
    public InputStream decode(String message) {
        byte[] bytes = Base64.getDecoder().decode(message);
        return new ByteArrayInputStream(bytes);
    }
}
