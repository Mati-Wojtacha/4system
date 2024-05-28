package com.application.user_management.unit.controllers;

import com.application.user_management.controllers.UserController;
import com.application.user_management.models.User;
import com.application.user_management.payload.requests.SearchSortPaginationConfig;
import com.application.user_management.payload.responses.PagingSortResponse;
import com.application.user_management.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserControllerTest {
    @InjectMocks
    private UserController userController;

    private final List<User> userList = Arrays.asList(
            new User("John", "Doe", "jon123"),
            new User("Alice", "Smith", "alice1")
    );
    private SearchSortPaginationConfig config;

    @Mock
    private UserService userServiceMock;

    @BeforeEach
    void setUp() {
        config = new SearchSortPaginationConfig(){
            {
                setPage(0);
                setSize(10);
            }
        };
        var response = new PagingSortResponse<User>(new PageImpl<User>(userList));

        when(userServiceMock.list(config)).thenReturn(response);
    }

    @Test
    void getAllUsersSorted_ShouldReturnBadRequest_WhenPagingSortingParamsIsNull() {
        var response = userController.getAllUsersSorted(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void getAllUsersSorted_ShouldReturnOkResponse_WhenPagingSortingParamsNotNullAndListSucceed() {
        ResponseEntity<?> response = userController.getAllUsersSorted(config);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        var sortResponse = (PagingSortResponse<User>) response.getBody();
        assertNotNull(sortResponse);
        assertEquals(userList.size(), sortResponse.getTotal());

        var sortedUsers = sortResponse.getData();
        assertNotNull(sortedUsers);
        assertEquals(userList.size(), sortedUsers.size());
        assertEquals(new HashSet<>(userList), new HashSet<>(sortedUsers));
    }
}