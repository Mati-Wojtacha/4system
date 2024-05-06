package com.application.java_4system.services;

import com.application.crud.CrudService;
import com.application.java_4system.models.User;
import com.application.java_4system.payload.responses.ProcessedDataResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService extends CrudService<User, Long> {
    ProcessedDataResult saveUsers(MultipartFile file);
}