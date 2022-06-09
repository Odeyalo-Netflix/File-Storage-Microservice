package com.odeyalo.analog.netflix.service.video;

import com.odeyalo.analog.netflix.exceptions.VideoNotFoundException;
import org.springframework.core.io.Resource;

import java.io.FileNotFoundException;

public interface VideoStreamService {

    Resource stream(String videoId) throws VideoNotFoundException, FileNotFoundException;

}
