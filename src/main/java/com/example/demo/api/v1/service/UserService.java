package com.example.demo.api.v1.service;

import com.example.demo.api.v1.dto.UserDto;
import com.example.demo.api.v1.entity.User;
import com.example.demo.api.v1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @NonNull
    public List<UserDto> getUsers() {
        return modelMapper.map(userRepository.findAll(), new TypeToken<List<UserDto>>() {}.getType());
    }

    @Nullable
    public UserDto getUserById(Long id) {
        if (id == null) {
            return null;
        }

        return userRepository.findById(id)
                .map(user -> modelMapper.map(user, UserDto.class))
                .orElse(null);
    }

    @NonNull
    public UserDto createUser(UserDto userDto) {
        return modelMapper.map(userRepository.save(modelMapper.map(userDto, User.class)), UserDto.class);
    }

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

    public boolean deleteUser(@NonNull Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
