package com.application.java_4system.controllers.servlet;

import com.application.java_4system.models.User;
import com.application.java_4system.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/user/*")
public class UserGetDeleteServlet extends HttpServlet {
    private final UserService userService;

    public UserGetDeleteServlet(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        Long userId = extractUserId(request);
        if (userId == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        boolean deleted = userService.deleteById(userId);
        if (deleted) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long userId = extractUserId(request);
        if (userId == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Optional<User> userResponse = userService.findById(userId);
        if (userResponse.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(userResponse.get());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private Long extractUserId(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            return null;
        }

        if (pathInfo.startsWith("/")) {
            pathInfo = pathInfo.substring(1);
        }

        Long userId;
        try {
            userId = Long.parseLong(pathInfo);
        } catch (NumberFormatException e) {
            return null;
        }

        return userId;
    }
}
