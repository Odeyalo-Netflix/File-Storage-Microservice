package com.odeyalo.analog.netflix.service.video;

public interface VideoRemoveService {
    /**
     * Delete specific video by video id
     * @param videoId - unique video id to delete
     */
    void delete(String videoId);
}
