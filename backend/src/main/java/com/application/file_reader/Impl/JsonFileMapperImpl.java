package com.application.file_reader.Impl;

import com.application.file_reader.FileMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class JsonFileMapperImpl implements FileMapper {

    public Collection<String> contentType(){
        return List.of("application/json");
    }

    public <T> Collection<T> readFile(InputStream file, Class<T> type) throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, type));
    }
}