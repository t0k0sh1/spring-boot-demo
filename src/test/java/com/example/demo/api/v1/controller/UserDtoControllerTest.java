package com.example.demo.api.v1.controller;

import com.example.demo.api.v1.dto.UserDto;
import com.example.demo.api.v1.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Description;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@ActiveProfiles("test")
class UserDtoControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @Description("GET /api/v1/users")
    class GetUsers {

        @Test
        @Description("Should return list of one user")
        void shouldReturnListOfOneUser() throws Exception {
            // given
            List<UserDto> expected = List.of(
                    new UserDto(1L, "James Bond", "james@example.com", "password")
            );

            // when
            when(userService.getUsers()).thenReturn(expected);

            // then
            mockMvc.perform(get("/api/v1/users/"))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(expected)));
        }

        @Test
        @Description("Should return list of two users")
        void shouldReturnListOfTwoUsers() throws Exception {
            // given
            List<UserDto> expected = List.of(
                    new UserDto(1L, "James Bond", "james@example.com", "password"),
                    new UserDto(2L, "Maria Jones", "maria@example.com", "password")
            );

            // when
            when(userService.getUsers()).thenReturn(expected);

            // then
            mockMvc.perform(get("/api/v1/users/"))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(expected)));
        }

        @Test
        @Description("Should return list of three users")
        void shouldReturnListOfThreeUsers() throws Exception {
            // given
            List<UserDto> expected = List.of(
                    new UserDto(1L, "James Bond", "james@example.com", "password"),
                    new UserDto(2L, "Maria Jones", "maria@example.com", "password"),
                    new UserDto(3L, "Anna Smith", "anna@example.com", "password")
            );

            // when
            when(userService.getUsers()).thenReturn(expected);

            // then
            mockMvc.perform(get("/api/v1/users/"))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(expected)));
        }

        @Test
        @Description("Should return no content")
        void shouldReturnNoContent() throws Exception {
            // given
            List<UserDto> expected = List.of();

            // when
            when(userService.getUsers()).thenReturn(expected);

            // then
            mockMvc.perform(get("/api/v1/users/"))
                    .andExpect(status().isNoContent())
                    .andExpect(content().json(objectMapper.writeValueAsString(expected)));
        }
    }

    @Nested
    @Description("GET /api/v1/users/{id}")
    class GetUserById {

        @Test
        @Description("Should return user with id 1")
        void shouldReturnUserWithId1() throws Exception {
            // given
            UserDto expected = new UserDto(1L, "James Bond", "james@example.com", "password");

            // when
            when(userService.getUserById(1L)).thenReturn(expected);

            // then
            mockMvc.perform(get("/api/v1/users/1"))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(expected)));
        }

        @Test
        @Description("Should return not found")
        void shouldReturnNotFound() throws Exception {
            // when
            when(userService.getUserById(4L)).thenReturn(null);

            // then
            mockMvc.perform(get("/api/v1/users/2"))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @Description("POST /api/v1/users")
    class CreateUser {

        @Test
        @Description("Should create user")
        void shouldCreateUser() throws Exception {
            // given
            UserDto userDto = new UserDto(null, "James Bond", "james@example.com", "password");
            UserDto expected = new UserDto(1L, "James Bond", "james@example.com", "password");

            // when
            when(userService.createUser(userDto)).thenReturn(expected);

            // then
            mockMvc.perform(post("/api/v1/users/")
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(userDto)))
                    .andExpect(status().isCreated())
                    .andExpect(content().json(objectMapper.writeValueAsString(expected)));
        }
    }

    @Nested
    @Description("PUT /api/v1/users/{id}")
    class UpdateUser {

        @Test
        @Description("Should update user")
        void shouldUpdateUser() throws Exception {
            // given
            UserDto userDto = new UserDto(1L, "James Bond", "james@example.com", "password");

            // when
            when(userService.updateUser(1L, userDto)).thenReturn(userDto);

            // then
            mockMvc.perform(put("/api/v1/users/1")
                            .contentType("application/json")
                            .content(objectMapper.writeValueAsString(userDto)))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(userDto)));
        }

        @Test
        @Description("Should return not found")
        void shouldReturnNotFound() throws Exception {
            // given
            UserDto userDto = new UserDto(1L, "James Bond", "james@example.com", "password");

            // when
            when(userService.updateUser(1L, null)).thenReturn(null);

            // then
            mockMvc.perform(put("/api/v1/users/1")
                            .contentType("application/json")
                            .content(objectMapper.writeValueAsString(userDto)))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @Description("DELETE /api/v1/users/{id}")
    class DeleteUser {

        @Test
        @Description("Should delete user")
        void shouldDeleteUser() throws Exception {
            // when
            when(userService.deleteUser(1L)).thenReturn(true);

            // then
            mockMvc.perform(delete("/api/v1/users/1"))
                    .andExpect(status().isNoContent());
        }

        @Test
        @Description("Should return not found")
        void shouldReturnNotFound() throws Exception {
            // when
            when(userService.deleteUser(1L)).thenReturn(false);

            // then
            mockMvc.perform(delete("/api/v1/users/1"))
                    .andExpect(status().isNotFound());
        }
    }
}