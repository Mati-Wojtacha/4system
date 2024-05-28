package com.application.user_management.services;

import com.application.crud.CrudService;
import com.application.user_management.models.User;
import com.application.user_management.payload.responses.ProcessedDataResult;
import org.springframework.web.multipart.MultipartFile;

public interface UserService extends CrudService<User, Long> {
    ProcessedDataResult saveUsers(MultipartFile file);
}