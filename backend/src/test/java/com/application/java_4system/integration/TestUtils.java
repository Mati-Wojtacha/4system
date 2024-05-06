package com.application.java_4system.integration;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;

public class TestUtils {

    public static MultipartFile prepareUnsupportedTestFile() {
        String content = "name,surname,login\n" +
                "John,Doe,johndoe\n" +
                "Jane,Smith,janesmith";

        return new MockMultipartFile("test.csv", "test.csv", "text/csv", content.getBytes(StandardCharsets.UTF_8));
    }

    public static MultipartFile prepareJsonTestFile() {
        String content = "[\n" +
                "  {\n" +
                "    \"name\": \"John\",\n" +
                "    \"surname\": \"Doe\",\n" +
                "    \"login\": \"johndoe\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"name\": \"Jane\",\n" +
                "    \"surname\": \"Smith\",\n" +
                "    \"login\": \"janesmith\"\n" +
                "  }\n" +
                "]";

        return new MockMultipartFile("test.json", "test.json", "application/json", content.getBytes(StandardCharsets.UTF_8));
    }

    public static MultipartFile prepareIncorrectJsonTestFile() {
        String content = "[\n" +
                "  {\n" +
                "    \"name\": \"\",\n" +
                "    \"surname\": \"Doe\",\n" +
                "    \"login\": \"johndoe\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"name\": \"\",\n" +
                "    \"surname\": \"\",\n" +
                "    \"login\": \"\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"name\": \"Jane\",\n" +
                "    \"surname\": \"Smith\",\n" +
                "    \"login\": \"janesmith\"\n" +
                "  }\n" +
                "]";

        return new MockMultipartFile("test.json", "test.json", "application/json", content.getBytes(StandardCharsets.UTF_8));
    }

    public static MockMultipartFile prepareXmlTestFile() {
        String content = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<users>\n" +
                "    <user>\n" +
                "        <name>John</name>\n" +
                "        <surname>Doe</surname>\n" +
                "        <login>johndoe</login>\n" +
                "    </user>\n" +
                "    <user>\n" +
                "        <name>Jane</name>\n" +
                "        <surname>Smith</surname>\n" +
                "        <login>janesmith</login>\n" +
                "    </user>\n" +
                "</users>";

        return new MockMultipartFile("test.xml", "test.xml", "application/xml", content.getBytes(StandardCharsets.UTF_8));
    }

    public static MockMultipartFile prepareIncorrectXmlTestFile() {
        String content = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<users>\n" +
                "    <user>\n" +
                "        <name></name>\n" +
                "        <surname>Doe</surname>\n" +
                "        <login>johndoe</login>\n" +
                "    </user>\n" +
                "    <user>\n" +
                "        <name></name>\n" +
                "        <surname></surname>\n" +
                "        <login></login>\n" +
                "    </user>\n" +
                "    <user>\n" +
                "        <name>Jane</name>\n" +
                "        <surname>Smith</surname>\n" +
                "        <login>janesmith</login>\n" +
                "    </user>\n" +
                "</users>";

        return new MockMultipartFile("test.xml", "test.xml", "application/xml", content.getBytes(StandardCharsets.UTF_8));
    }
}