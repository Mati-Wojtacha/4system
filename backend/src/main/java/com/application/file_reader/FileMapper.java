package com.application.file_reader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

public interface FileMapper {

    Collection<String> contentType();

    <T> Collection<T> readFile(InputStream file, Class<T> type) throws IOException;
}