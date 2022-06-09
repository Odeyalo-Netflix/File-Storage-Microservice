package com.odeyalo.analog.netflix.service.decoder;

import java.io.IOException;

/**
 *
 * @param <R> return value
 * @param <T> method param
 */
public interface KafkaMessageDecoder<R, T> {

    R decode(T message) throws IOException;
}
