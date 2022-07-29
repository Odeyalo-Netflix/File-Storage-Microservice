package com.odeyalo.analog.netflix.support;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UUIDFileNameGenerator implements FileNameGenerator {

    @Override
    public String generateName() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
