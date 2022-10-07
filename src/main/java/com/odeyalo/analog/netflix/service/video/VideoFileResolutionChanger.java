package com.odeyalo.analog.netflix.service.video;

/**
 * Change video file to given resolution
 */
public interface VideoFileResolutionChanger {

    String LIBX264 = "libx264";

    /**
     * Same to changeVideoResolution(String, String, String, String, boolean, Integer, Integer) but use default video codec(libx264)
     */
    default void changeVideoResolution(String originalFilePath, String outputPath, String outputFormat,
                                 boolean overrideExist, Integer height, Integer width) throws Exception {
        changeVideoResolution(originalFilePath, outputPath, outputFormat, LIBX264, overrideExist, height, width);
    }
    /**
     *
     * @param originalFilePath - path to file with video
     * @param outputPath  - path to save processed video
     * @param overrideExist - true if existed file needs to be override
     * @param outputFormat - file type to create
     * @param videoCodec - video codec, libx264 for example
     * @param height - required height
     * @param width - required width
     * @throws Exception - if any exception was occurred during processing
     */
    void changeVideoResolution(String originalFilePath, String outputPath, String outputFormat, String videoCodec, boolean overrideExist, Integer height, Integer width) throws Exception;

}
