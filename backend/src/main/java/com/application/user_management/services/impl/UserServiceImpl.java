package com.application.user_management.services.impl;


import com.application.crud.CrudServiceBase;
import com.application.file_reader.FileMapper;
import com.application.user_management.models.User;
import com.application.user_management.payload.requests.SearchSortPaginationConfig;
import com.application.user_management.payload.responses.PagingSortResponse;
import com.application.user_management.repository.UserRepository;
import com.application.user_management.payload.responses.ResponseError;
import com.application.user_management.payload.responses.ProcessedDataResult;

import com.application.user_management.services.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public final class UserServiceImpl extends CrudServiceBase<User, Long, UserRepository> implements UserService {

    private final UserRepository repository;
    private final Logger logger;
    private final List<FileMapper> fileMappers;

    public UserServiceImpl(UserRepository repository, List<FileMapper> fileMappers) {
        super(repository);
        logger = LoggerFactory.getLogger(UserServiceImpl.class);
        this.repository = repository;
        this.fileMappers = fileMappers;
    }

    @Override
    protected Collection<String> getColumnNames() {
        return Arrays.asList("name", "surname", "login");
    }

    @Override
    public PagingSortResponse<User> list(SearchSortPaginationConfig searchSortPaginationConfig) {
        PagingSortResponse<User> response = super.list(searchSortPaginationConfig);
        List<User> users = response.getData();
        for (User user : users) {
            if(user.getName()!=null && !user.getName().isEmpty()) {
                user.setSurname(user.getSurname() + "_" + DigestUtils.md5Hex(user.getName()));
            }
        }
        response.setData(users);

        return response;
    }

    public ProcessedDataResult saveUsers(MultipartFile file) {
        try {
            var users = readUsers(file);

            return processUserData(users);
        } catch (Exception e) {
            logger.error("Error while reading file", e);

            return new ProcessedDataResult(new ResponseError("Error while reading file", ResponseError.EnumMessage.VALIDATION_ERROR_OR_UNSUPPORTED_FILE));
        }
    }

    private Collection<User> readUsers(MultipartFile file) throws Exception {
        var extension = file.getContentType();

        for (FileMapper mapper : fileMappers) {
            if (mapper.contentType().contains(extension)) {
                var inputStream = file.getInputStream();

                return mapper.readFile(inputStream, User.class);
            }
        }

        throw new IllegalArgumentException(String.format("Unsupported extension: %s", extension));
    }

    private ProcessedDataResult processUserData(Collection<User> userList) {
        var result = new ProcessedDataResult();
        result.setTotalObjects(userList.size());

        Collection<User> userToRemove = new ArrayList<>();

        for (var user : userList) {
            if (isAllFieldNullOrEmptyUser(user)) {
                userToRemove.add(user);
            }
        }

        result.setNullableObjects(userToRemove.size());
        userList.removeAll(userToRemove);

        repository.saveAll(userList);

        var usersWithNullData = new ArrayList<User>();

        for (var user : userList) {
            if (isAnyFieldNullOrEmpty(user)) {
                usersWithNullData.add(user);
            }
        }

        result.setTotalProcessedObjects(userList.size());
        result.setIncompleteObjects(usersWithNullData);

        return result;
    }

    private boolean isAnyFieldNullOrEmpty(User user) {
        return isEmptyOrNull(user.getName()) || isEmptyOrNull(user.getSurname()) || isEmptyOrNull(user.getLogin());
    }

    private boolean isAllFieldNullOrEmptyUser(User user) {
        return isEmptyOrNull(user.getName()) && isEmptyOrNull(user.getSurname()) && isEmptyOrNull(user.getLogin());
    }

    private boolean isEmptyOrNull(String str) {
        return str == null || str.isEmpty();
    }
}
