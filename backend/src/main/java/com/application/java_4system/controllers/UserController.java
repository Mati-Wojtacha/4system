package com.application.java_4system.controllers;

import com.application.java_4system.payload.responses.ResponseError;
import com.application.java_4system.services.UserService;
import com.application.java_4system.models.User;
import com.application.java_4system.payload.requests.SearchSortPaginationConfig;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public final class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllUsersSorted(SearchSortPaginationConfig searchSortPaginationConfig) {
        if (searchSortPaginationConfig == null){
            return ResponseEntity.badRequest().body("searchSortPaginationConfig is required");
        }

        return ResponseEntity.ok(userService.list(searchSortPaginationConfig));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        var userResponse = userService.findById(id);
        if (userResponse.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userResponse);
    }

    @PutMapping()
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        var userResponse = userService.update(user);
        if (userResponse == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userResponse);
    }

    @PostMapping()
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.save(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        if (userService.deleteById(id)) {

            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/saveFromFile")
    public ResponseEntity<?> saveUserFromFile(@RequestBody MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body(new ResponseError("File is empty", ResponseError.EnumMessage.EMPTY_FILE));
        }

        var result = userService.saveUsers(file);
        if (result.getError() != null) {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(result.getError());
        }

        return ResponseEntity.ok(result);
    }
}