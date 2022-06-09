package com.odeyalo.analog.netflix.service.video;

import com.odeyalo.analog.netflix.exceptions.VideoNotFoundException;

import java.io.IOException;

public interface VideoStreamingService<T> {

    T stream(String videoId, String range) throws VideoNotFoundException, IOException;

}
