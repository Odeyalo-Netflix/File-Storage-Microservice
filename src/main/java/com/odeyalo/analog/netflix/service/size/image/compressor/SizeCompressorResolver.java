package com.odeyalo.analog.netflix.service.size.image.compressor;

import java.io.IOException;
import java.io.InputStream;

public interface SizeCompressorResolver {

    String compress(InputStream image) throws IOException;

}
