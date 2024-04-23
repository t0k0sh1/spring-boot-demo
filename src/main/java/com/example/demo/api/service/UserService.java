package com.example.demo.api.service;

import com.example.demo.api.dto.UserDto;
import com.example.demo.api.entity.User;
import com.example.demo.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The UserService class is a service that handles user-related operations.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    /**
     * Retrieves all users.
     *
     * @return a list of users
     */
    @NonNull
    public List<UserDto> getUsers() {
        return modelMapper.map(userRepository.findAll(), new TypeToken<List<UserDto>>() {}.getType());
    }

    /**
     * Retrieves a user by ID.
     *
     * @param id the ID of the user to retrieve
     * @return the user with the specified ID
     */
    @Nullable
    public UserDto getUserById(Long id) {
        if (id == null) {
            return null;
        }

        return userRepository.findById(id)
                .map(user -> modelMapper.map(user, UserDto.class))
                .orElse(null);
    }

    /**
     * Creates a new user.
     *
     * @param userDto the user to create
     * @return the created user
     */
    @NonNull
    public UserDto createUser(UserDto userDto) {
        return modelMapper.map(userRepository.save(modelMapper.map(userDto, User.class)), UserDto.class);
    }

    /**
     * Updates a user by ID.
     *
     * @param id the ID of the user to update
     * @param userDto the updated user
     * @return the updated user
     */
    @Nullable
    public UserDto updateUser(Long id, UserDto userDto) {
        if (id == null || userDto == null) {
            return null;
        }

        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(userDto.getUsername());
                    user.setEmail(userDto.getEmail());
                    user.setPassword(userDto.getPassword());
                    return modelMapper.map(userRepository.save(user), UserDto.class);
                })
                .orElse(null);
    }

    /**
     * Deletes a user by ID.
     *
     * @param id the ID of the user to delete
     * @return true if the user was deleted, false otherwise
     */
    public boolean deleteUser(Long id) {
        if (id == null) {
            return false;
        }

        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
