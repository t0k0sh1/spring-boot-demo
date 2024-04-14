package com.example.demo.api.v1.controller;

import com.example.demo.api.v1.dto.UserDto;
import com.example.demo.api.v1.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> users = userService.getUsers();
        if (users.isEmpty()) {
            return new ResponseEntity<>(users, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        UserDto user = userService.getUserById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }

    @PostMapping(value = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserDto user = userService.createUser(userDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        UserDto user = userService.updateUser(id, userDto);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userService.deleteUser(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
