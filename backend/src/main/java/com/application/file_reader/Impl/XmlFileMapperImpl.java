package com.application.file_reader.Impl;

import com.application.file_reader.FileMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;

@Service
public class XmlFileMapperImpl implements FileMapper {

    public Collection<String> contentType(){return List.of("application/xml","text/xml");}

    public <T> Collection<T> readFile(InputStream file, Class<T> type) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();

        return xmlMapper.readValue(file, xmlMapper.getTypeFactory().constructCollectionType(Collection.class, type));
    }
}