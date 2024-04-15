package com.example.demo.api.v1.controller;

import com.example.demo.api.v1.dto.UserDto;
import com.example.demo.api.v1.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The UserController class is a REST controller that handles HTTP requests related to users.
 */
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    /**
     * Retrieves all users.
     *
     * @return a list of users
     */
    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> users = userService.getUsers();
        if (users.isEmpty()) {
            return new ResponseEntity<>(users, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
    }

    /**
     * Retrieves a user by ID.
     *
     * @param id the ID of the user to retrieve
     * @return the user with the specified ID
     */
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        UserDto user = userService.getUserById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }

    /**
     * Creates a new user.
     *
     * @param user the user to create
     * @return the created user
     */
    @PostMapping(value = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto user) {
        UserDto newUser = userService.createUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    /**
     * Updates a user by ID.
     *
     * @param id the ID of the user to update
     * @param user the updated user
     * @return the updated user, or not found if the user was not found
     */
    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto user) {
        UserDto updatedUser = userService.updateUser(id, user);
        if (updatedUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }
    }

    /**
     * Deletes a user by ID.
     *
     * @param id the ID of the user to delete
     * @return a response entity with no content if the user was deleted, or not found if the user was not found
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userService.deleteUser(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
