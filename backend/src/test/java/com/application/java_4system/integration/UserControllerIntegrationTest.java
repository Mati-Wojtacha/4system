package com.application.java_4system.integration;

import com.application.java_4system.models.User;
import com.application.java_4system.payload.requests.SearchSortPaginationConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.lang.reflect.Field;

import static com.application.java_4system.integration.TestUtils.prepareJsonTestFile;
import static com.application.java_4system.integration.TestUtils.prepareUnsupportedTestFile;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "classpath:test-data.sql")
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    void getUserById_ShouldReturnUserAndStatus200_WhenIdExists() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/1")).andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    void getUserById_ShouldReturnStatus404_WhenIdNotExists() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/1111")).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void getAllUsersSorted_ShouldReturnStatus200_WhenConfigIsNotNull() throws Exception {
        SearchSortPaginationConfig searchSortPaginationConfig = new SearchSortPaginationConfig(){
            {
                setPage(0);
                setSize(10);
            }
        };
        mockMvc.perform(MockMvcRequestBuilders.get("/user/list").contentType(MediaType.APPLICATION_JSON).content(String.valueOf(searchSortPaginationConfig))).andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    void getAllUsersSorted_ShouldReturnStatus200_WhenConfigIsNull() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/list")).andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    void saveUserFromFile_ShouldReturnStatus200_WhenFileIsSupported() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.json", MediaType.APPLICATION_JSON_VALUE, prepareJsonTestFile().getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/user/saveFromFile").file(file)).andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    void saveUserFromFile_ShouldReturnStatus415_WhenFileIsUnsupported() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.json", MediaType.APPLICATION_JSON_VALUE, prepareUnsupportedTestFile().getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/user/saveFromFile").file(file)).andExpect(MockMvcResultMatchers.status().isUnsupportedMediaType());
    }

    @Test
    void saveUserFromFile_ShouldReturnStatus400_WhenFileIsEmpty() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.json", MediaType.APPLICATION_JSON_VALUE, new byte[0]);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/user/saveFromFile").file(file)).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void saveUser_ShouldReturnUserAndStatus200_WhenUserIsValid() throws Exception {
        User user = new User(){
            {
                setLogin("userToSave_login");
                setName("userToSave_name");
                setSurname("userToSave_surname");
            }
        };

        var response = mockMvc.perform(MockMvcRequestBuilders.post("/user").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(user))).andExpect(MockMvcResultMatchers.status().isOk());
        var userSaved = new XmlMapper().readValue(response.andReturn().getResponse().getContentAsString(), User.class);

        assertThat(userSaved).isNotNull();
        assertThat(userSaved.getLogin()).isEqualTo(user.getLogin());
        assertThat(userSaved.getName()).isEqualTo(user.getName());
        assertThat(userSaved.getSurname()).isEqualTo(user.getSurname());
    }

    @Test
    void updateUser_ShouldReturnUpdatedUserAndStatus200_WhenIdIs3() throws Exception {
        User user = new User();
        Field field = user.getClass().getSuperclass().getDeclaredField("id");
        field.setAccessible(true);
        field.set(user, 3L);
        user.setLogin("loginAfterUpdate");
        user.setName("nameAfterUpdate");
        user.setSurname("surnameAfterUpdate");
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders.put("/user").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(user))).andExpect(MockMvcResultMatchers.status().isOk());
        var response = mockMvc.perform(MockMvcRequestBuilders.get("/user/3").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        var userUpdated = objectMapper.readValue(response.getContentAsString(), User.class);

        assertThat(userUpdated).isNotNull();
        assertThat(userUpdated.getLogin()).isEqualTo(user.getLogin());
        assertThat(userUpdated.getName()).isEqualTo(user.getName());
        assertThat(userUpdated.getSurname()).isEqualTo(user.getSurname());
    }


    @Test
    void deleteUser_ShouldReturnStatus200AndStatus404OnReFetch_WhenIdIs4() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/user/4")).andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get("/user/4")).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}