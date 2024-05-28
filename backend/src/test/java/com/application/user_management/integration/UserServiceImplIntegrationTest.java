package com.application.user_management.integration;

import com.application.user_management.Application;
import com.application.user_management.models.User;
import com.application.user_management.payload.requests.SearchSortPaginationConfig;
import com.application.user_management.payload.responses.PagingSortResponse;
import com.application.user_management.payload.responses.ProcessedDataResult;
import com.application.user_management.payload.responses.ResponseError;
import com.application.user_management.services.impl.UserServiceImpl;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static com.application.user_management.integration.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = Application.class)
@Transactional
class UserServiceImplIntegrationTest {

    @Autowired
    private UserServiceImpl userService;

    @Test
    void saveUsers_ShouldReturnProcessedDataResult_WhenFileIsUnsupported() {
        MultipartFile file = prepareUnsupportedTestFile();

        ProcessedDataResult result = userService.saveUsers(file);

        assertEquals("Error while reading file", result.getError().getMessage());
        assertEquals(ResponseError.EnumMessage.VALIDATION_ERROR_OR_UNSUPPORTED_FILE, result.getError().getEnumMessage());
    }

    @Test
    void saveUsers_ShouldReturnProcessedDataResult_WhenJsonFileIsIncorrect() {
        MultipartFile file = prepareIncorrectJsonTestFile();

        ProcessedDataResult result = userService.saveUsers(file);

        assertEquals(3, result.getTotalObjects());
        assertEquals(1, result.getNullableObjects());
        assertEquals(2, result.getTotalProcessedObjects());
        assertEquals(1, result.getIncompleteObjects().size());
    }

    @Test
    void saveUsers_ShouldReturnProcessedDataResult_WhenXmlFileIsIncorrect() throws Exception {
        MockMultipartFile file = prepareIncorrectXmlTestFile();

        ProcessedDataResult result = userService.saveUsers(file);

        assertEquals(3, result.getTotalObjects());
        assertEquals(1, result.getNullableObjects());
        assertEquals(2, result.getTotalProcessedObjects());
        assertEquals(1, result.getIncompleteObjects().size());
    }

    @Test
    void saveUsers_ShouldReturnProcessedDataResult_WhenFileJsonIsCorrect() {
        MultipartFile file = prepareJsonTestFile();

        ProcessedDataResult result = userService.saveUsers(file);

        assertEquals(2, result.getTotalObjects());
        assertEquals(0, result.getNullableObjects());
        assertEquals(2, result.getTotalProcessedObjects());
        assertEquals(0, result.getIncompleteObjects().size());
    }


    @Test
    void saveUsers_ShouldReturnProcessedDataResult_WhenXmlFileIsCorrect() throws Exception {
        MockMultipartFile file = prepareXmlTestFile();

        ProcessedDataResult result = userService.saveUsers(file);

        assertEquals(2, result.getTotalObjects());
        assertEquals(0, result.getNullableObjects());
        assertEquals(2, result.getTotalProcessedObjects());
        assertEquals(0, result.getIncompleteObjects().size());
    }

    @Test
    void saveUser_ShouldReturnSingleUserWithMD5HashedName_WhenUserIsSaved() {
        User user = new User();
        user.setName("John");
        user.setSurname("Doe");
        user.setLogin("johndoe");

        User userAdded = userService.save(user);

        SearchSortPaginationConfig config = new SearchSortPaginationConfig();
        PagingSortResponse<User> response = userService.list(config);

        assertEquals(1, response.getData().size());
        assertEquals(user.getName(), response.getData().get(0).getName());
        assertEquals("Doe_61409aa1fd47d4a5332de23cbf59a36f", response.getData().get(0).getSurname());
        assertEquals(user.getLogin(), response.getData().get(0).getLogin());
    }
}