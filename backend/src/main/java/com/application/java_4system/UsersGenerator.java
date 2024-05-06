package com.application.java_4system;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UsersGenerator {
    public static void main(String[] args) {
        fileJsonUserGenerator(50);
        fileXMLUserGenerator(200000);
    }

    private static void fileJsonUserGenerator(int numberOfUsers) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < numberOfUsers; i++) {
            User user = new User("Name" + i, "Surname" + i, "Login" + i);
            users.add(user);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File("users_" + numberOfUsers + "_" + UUID.randomUUID() + ".json"), users);
            System.out.println("Json file was generated.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void fileXMLUserGenerator(int numberOfUsers) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < numberOfUsers; i++) {
            User user = new User("Name" + i, "Surname" + i, "Login" + i);
            users.add(user);
        }
        try {
            FileWriter writer = getFileWriter(numberOfUsers, users);
            writer.close();
            System.out.println("XML file was generated.");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static FileWriter getFileWriter(int numberOfUsers, List<User> users) throws IOException {
        File file = new File("users_" + numberOfUsers + "_" + UUID.randomUUID() + ".xml");
        FileWriter writer = new FileWriter(file);
        writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        writer.write("<users>\n");

        for (User user : users) {
            writer.write("  <user>\n");
            writer.write("    <name>" + user.getName() + "</name>\n");
            writer.write("    <surname>" + user.getSurname() + "</surname>\n");
            writer.write("    <login>" + user.getLogin() + "</login>\n");
            writer.write("  </user>\n");
        }

        writer.write("</users>");
        return writer;
    }

    @Data
    @AllArgsConstructor
    static class User {
        private String name;
        private String surname;
        private String login;
    }

}
