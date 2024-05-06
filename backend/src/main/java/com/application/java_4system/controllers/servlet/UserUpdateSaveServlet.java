package com.application.java_4system.controllers.servlet;

import com.application.java_4system.models.User;
import com.application.java_4system.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/user")
public class UserUpdateSaveServlet extends HttpServlet {

    private final UserService userService;

    public UserUpdateSaveServlet(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestBody = extractBody(request);
        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(requestBody, User.class);
        User savedUser = userService.save(user);

        prepareResponse(response, objectMapper.writeValueAsString(savedUser), HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String requestBody = extractBody(req);
        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(requestBody, User.class);
        User userResponse = userService.update(user);

        if (userResponse == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            prepareResponse(resp, null, HttpServletResponse.SC_OK);
        }
    }

    private String extractBody(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    private void prepareResponse(HttpServletResponse response, String responseBody, int status) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        if (responseBody != null) {
            response.getWriter().write(responseBody);
        }
        response.setStatus(status);
    }

}

