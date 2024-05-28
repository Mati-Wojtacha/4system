package com.application.user_management.controllers.servlet;

import com.application.user_management.models.CustomMultipartFile;
import com.application.user_management.payload.responses.ResponseError;
import com.application.user_management.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.*;

@WebServlet("/user/saveFromFile")
@MultipartConfig
public class UserSaveFromFileServlet extends HttpServlet {

    private final UserService userService;

    public UserSaveFromFileServlet(UserService userService) {
        this.userService = userService;
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Part filePart = req.getPart("file");

        resp.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();

        if(filePart == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            ResponseError error = new ResponseError("File is empty", ResponseError.EnumMessage.EMPTY_FILE);

            String errorJson = mapper.writeValueAsString(error);

            resp.getWriter().write(errorJson);
            return;
        }

        InputStream fileContent = filePart.getInputStream();


        byte[] inputArray = fileContent.readAllBytes();
        var multipartFile = new CustomMultipartFile(inputArray, filePart.getContentType(), filePart.getSubmittedFileName());

        var result = userService.saveUsers(multipartFile);
        if (result.getError() != null) {
            resp.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
            resp.getWriter().write(result.getError().toString());
            return;
        }

        mapper.writeValue(resp.getWriter(), result);
    }

}
