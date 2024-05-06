package com.application.java_4system.unit.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.application.crud.CrudServiceBase;
import com.application.java_4system.models.User;
import com.application.java_4system.payload.requests.SearchSortPaginationConfig;
import com.application.java_4system.payload.requests.SortCriteria;
import com.application.java_4system.payload.responses.PagingSortResponse;
import com.application.java_4system.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

public class CrudServiceBaseTest {
    private CrudServiceBase<User, Long, UserRepository> crudService;

    @Mock
    private UserRepository repository;

    private final List<User> userList = Arrays.asList(
            new User("John", "Doe", "jon123"),
            new User("Alice", "Smith", "alice1")
    );

    private SearchSortPaginationConfig config;

    @BeforeEach
    public void setup() {
        repository = mock(UserRepository.class);

        crudService = new CrudServiceBase<>(repository) {
            @Override
            protected List<String> getColumnNames() {
                return Arrays.asList("name", "email", "login");
            }
        };

        config = new SearchSortPaginationConfig() {
            {
                setPage(0);
                setSize(10);
            }
        };
    }

    @Test
    void list_ShouldReturnUsers_WhenPagingSortingParamsNotNullAndListSucceeds() {
        when(repository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(userList));

        PagingSortResponse<User> response = crudService.list(config);

        List<User> result = response.getData();

        assertEquals(2, result.size());
        assertEquals(userList, result);
    }



    @Test
    public void list_ShouldReturnSortedUsers_WhenPagingSortingParamsNotNullAndSortCriteriaIsNotEmpty() {
        when(repository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(userList));

        config.setSortCriteria(List.of(new SortCriteria("name", Sort.Direction.ASC)));
        PagingSortResponse<User> result = crudService.list(config);

        assertEquals(2, result.getTotal());
        assertEquals(userList, result.getData());
    }

    @Test
    void list_ShouldReturnUsers_WhenPagingSortingParamsIsNotNullAndSearchTermIsNotNullAndListSucceeds() {
        when(repository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.singletonList(userList.getFirst())));

        config.setSearchTerm("Doe");
        PagingSortResponse<User> response = crudService.list(config);

        List<User> result = response.getData();

        assertEquals(1, result.size());
        assertEquals(userList.getFirst(), result.getFirst());
    }

    @Test
    public void list_ShouldReturnUsers_WhenPagingSortingParamsIsNullAndListSucceeds() {
        when(repository.findAll()).thenReturn(userList);

        var result = crudService.list();

        assertEquals(2, result.size());
        assertEquals(userList.get(0), result.get(0));
        assertEquals(userList.get(1), result.get(1));
    }

    @Test
    public void save_ShouldReturnSavedEntity_WhenEntityIsValid() {
        var newUser = new User("example_name", "example_surname", "example_login");

        when(repository.save(newUser)).thenReturn(newUser);

        var result = crudService.save(newUser);

        assertEquals(newUser, result);
    }

    @Test
    public void update_ShouldReturnUpdatedEntity_WhenEntityExists() {
        User updatedEntity = new User("Updated User", "updated surname", "updated login");

        when(repository.findById(any())).thenReturn(Optional.of(userList.getFirst()));
        when(repository.save(updatedEntity)).thenReturn(updatedEntity);

        var result = crudService.update(updatedEntity);

        assertEquals(updatedEntity, result);
    }

    @Test
    public void update_ShouldReturnNull_WhenEntityDoesNotExist() {
        User updatedEntity = new User("Updated User", "updated surname", "updated login");

        when(repository.findById(any())).thenReturn(Optional.empty());

        var result = crudService.update(updatedEntity);

        assertNull(result);
    }

    @Test
    public void deleteById_ShouldReturnTrue_WhenEntityExists() {
        when(repository.findById(1L)).thenReturn(Optional.of(userList.get(1)));

        var result = crudService.deleteById(1L);

        assertTrue(result);
    }

    @Test
    public void deleteById_ShouldReturnFalse_WhenEntityDoesNotExist() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        var result = crudService.deleteById(1L);

        assertFalse(result);
    }
}