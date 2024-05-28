package com.application.user_management.unit.services;

import com.application.file_reader.FileMapper;
import com.application.user_management.models.User;
import com.application.user_management.payload.requests.SearchSortPaginationConfig;
import com.application.user_management.payload.responses.PagingSortResponse;
import com.application.user_management.payload.responses.ProcessedDataResult;
import com.application.user_management.payload.responses.ResponseError;
import com.application.user_management.repository.UserRepository;
import com.application.user_management.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.data.jpa.domain.Specification;


import java.io.InputStream;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private FileMapper xmlFileMapper;

    @Mock
    private FileMapper jsonFileMapper;

    private List<User> userList;

    private SearchSortPaginationConfig config;

    @BeforeEach
    void setUp() {
        config = new SearchSortPaginationConfig() {
            {
                setPage(0);
                setSize(10);
            }
        };

        userList = new ArrayList<>(){
            {
                add(new User("John", "Doe", "jon123"));
                add(new User("Alice", "Smith", "alice1"));
            }
        };
    }

    @Test
    void list_ShouldReturnUsersWithHashedSurnames_WhenPagingSortingParamsNotNullAndListSucceeds() {
        when(userRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(userList));
        UserServiceImpl userService = new UserServiceImpl(userRepository, Collections.emptyList());

        PagingSortResponse<User> response = userService.list(config);

        List<User> modifiedUsers = response.getData();
        assertEquals(2, modifiedUsers.size());

        assertEquals("Doe_61409aa1fd47d4a5332de23cbf59a36f", modifiedUsers.get(0).getSurname());
        assertEquals("Smith_64489c85dc2fe0787b85cd87214b3810", modifiedUsers.get(1).getSurname());
    }

    @Test
    void saveUsers_ShouldReturnError_WhenFileIsUnsupported() {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "test".getBytes());

        UserServiceImpl userService = new UserServiceImpl(userRepository, Collections.emptyList());

        ProcessedDataResult result = userService.saveUsers(file);

        assertEquals("Error while reading file", result.getError().getMessage());
        assertEquals(ResponseError.EnumMessage.VALIDATION_ERROR_OR_UNSUPPORTED_FILE, result.getError().getEnumMessage());
    }

    @Test
    void saveUsers_ShouldReturnError_WhenFileIsEmpty() {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "".getBytes());

        UserServiceImpl userService = new UserServiceImpl(userRepository, Collections.emptyList());

        ProcessedDataResult result = userService.saveUsers(file);

        assertEquals("Error while reading file", result.getError().getMessage());
        assertEquals(ResponseError.EnumMessage.VALIDATION_ERROR_OR_UNSUPPORTED_FILE, result.getError().getEnumMessage());
    }

    @Test
    void saveUsers_ShouldReturnProcessedDataResult_WhenFileIsXmlAndIncomplete() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "test.xml", "application/xml",
                "<users><user><name>John</name><surname>Doe</surname></user><user><name>Alice</name><surname>Smith</surname><login>alicesmith</login></user></users>".getBytes());
        when(xmlFileMapper.contentType()).thenReturn(Collections.singletonList("application/xml"));

        userList.add(new User("John", "Doe", null));

        when(xmlFileMapper.readFile(any(InputStream.class), any(Class.class))).thenReturn(userList);

        UserServiceImpl userService = new UserServiceImpl(userRepository, List.of(xmlFileMapper, jsonFileMapper));

        ProcessedDataResult result = userService.saveUsers(file);

        assertEquals(3, result.getTotalObjects());
        assertEquals(0, result.getNullableObjects());
        assertEquals(3, result.getTotalProcessedObjects());
        assertEquals(1, result.getIncompleteObjects().size());
    }

    @Test
    void saveUsers_ShouldReturnProcessedDataResult_WhenFileIsXmlAndHasNullValue() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "test.xml", "application/xml",
                "<users><user><name>John</name><surname>Doe</surname></user><user><name>Alice</name><surname>Smith</surname><login>alicesmith</login></user></users>".getBytes());
        when(xmlFileMapper.contentType()).thenReturn(Collections.singletonList("application/xml"));

        userList.add(new User());

        when(xmlFileMapper.readFile(any(InputStream.class), any(Class.class))).thenReturn(userList);

        UserServiceImpl userService = new UserServiceImpl(userRepository, List.of(xmlFileMapper, jsonFileMapper));

        ProcessedDataResult result = userService.saveUsers(file);

        assertEquals(3, result.getTotalObjects());
        assertEquals(1, result.getNullableObjects());
        assertEquals(2, result.getTotalProcessedObjects());
        assertEquals(0, result.getIncompleteObjects().size());
    }

    @Test
    void saveUsers_ShouldReturnProcessedDataResult_WhenFileIsXml() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "test.xml", "application/xml",
                "<users><user><name>John</name><surname>Doe</surname><login>johndoe</login></user><user><name>Alice</name><surname>Smith</surname><login>alicesmith</login></user></users>".getBytes());

        when(xmlFileMapper.contentType()).thenReturn(Collections.singletonList("application/xml"));
        when(xmlFileMapper.readFile(any(InputStream.class), any(Class.class))).thenReturn(userList);

        UserServiceImpl userService = new UserServiceImpl(userRepository, List.of(xmlFileMapper, jsonFileMapper));

        ProcessedDataResult result = userService.saveUsers(file);

        assertEquals(2, result.getTotalObjects());
        assertEquals(0, result.getNullableObjects());
        assertEquals(2, result.getTotalProcessedObjects());
        assertEquals(0, result.getIncompleteObjects().size());
    }

    @Test
    void saveUsers_ShouldReturnProcessedDataResult_WhenFileIsJson() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "test.json", "application/json",
                "[{\"name\":\"John\",\"surname\":\"Doe\",\"login\":\"johndoe\"},{\"name\":\"Alice\",\"surname\":\"Smith\",\"login\":\"alicesmith\"}]".getBytes());

        when(jsonFileMapper.contentType()).thenReturn(Collections.singletonList("application/json"));
        when(jsonFileMapper.readFile(any(InputStream.class), any(Class.class))).thenReturn(userList);

        UserServiceImpl userService = new UserServiceImpl(userRepository, List.of(xmlFileMapper, jsonFileMapper));

        ProcessedDataResult result = userService.saveUsers(file);

        assertEquals(2, result.getTotalObjects());
        assertEquals(0, result.getNullableObjects());
        assertEquals(2, result.getTotalProcessedObjects());
        assertEquals(0, result.getIncompleteObjects().size());
    }

    @Test
    void saveUsers_ShouldReturnProcessedDataResult_WhenFileIsJsonAndIncomplete() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "test.json", "application/json",
                "[{\"name\":\"John\",\"surname\":\"Doe\"},{\"name\":\"Alice\",\"surname\":\"Smith\",\"login\":\"alicesmith\"}]".getBytes());

        when(jsonFileMapper.contentType()).thenReturn(Collections.singletonList("application/json"));

        userList.add(new User("John", "Doe", null));
        when(jsonFileMapper.readFile(any(InputStream.class), any(Class.class))).thenReturn(userList);

        UserServiceImpl userService = new UserServiceImpl(userRepository, List.of(xmlFileMapper, jsonFileMapper));

        ProcessedDataResult result = userService.saveUsers(file);

        assertEquals(3, result.getTotalObjects());
        assertEquals(0, result.getNullableObjects());
        assertEquals(3, result.getTotalProcessedObjects());
        assertEquals(1, result.getIncompleteObjects().size());
    }

    @Test
    void saveUsers_ShouldReturnProcessedDataResult_WhenFileIsJsonAndHasNullValue() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "test.json", "application/json",
                "[{\"name\":\"John\",\"surname\":\"Doe\"},{\"name\":\"Alice\",\"surname\":\"Smith\",\"login\":\"alicesmith\"}]".getBytes());

        when(jsonFileMapper.contentType()).thenReturn(Collections.singletonList("application/json"));

        userList.add(new User());
        when(jsonFileMapper.readFile(any(InputStream.class), any(Class.class))).thenReturn(userList);

        UserServiceImpl userService = new UserServiceImpl(userRepository, List.of(xmlFileMapper, jsonFileMapper));

        ProcessedDataResult result = userService.saveUsers(file);

        assertEquals(3, result.getTotalObjects());
        assertEquals(1, result.getNullableObjects());
        assertEquals(2, result.getTotalProcessedObjects());
        assertEquals(0, result.getIncompleteObjects().size());
    }
}