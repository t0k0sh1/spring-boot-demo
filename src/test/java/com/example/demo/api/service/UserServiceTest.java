package com.example.demo.api.service;

import com.example.demo.api.dto.UserDto;
import com.example.demo.api.service.UserService;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Nested
    @Description("Get users")
    class GetUsers {

        @Test
        @Description("Should return list of one user")
        @DatabaseSetup("classpath:/dbunit/UserServiceTest/getUsers/shouldReturnListOfOneUser.xml")
        public void shouldReturnListOfOneUser() {
            // given
            List<UserDto> expected = List.of(
                    new UserDto(1L, "admin", "admin@example.com", "password")
            );

            // when
            var users = userService.getUsers();

            // then
            assertEquals(1, users.size());
            assertEquals(expected, users);
        }

        @Test
        @Description("Should return list of two users")
        @DatabaseSetup("classpath:/dbunit/UserServiceTest/getUsers/shouldReturnListOfTwoUsers.xml")
        public void shouldReturnListOfTwoUsers() {
            // given
            List<UserDto> expected = List.of(
                    new UserDto(1L, "admin", "admin@example.com", "password"),
                    new UserDto(2L, "user01", "user01@example.com", "password")
            );
            // when
            var users = userService.getUsers();

            // then
            assertEquals(2, users.size());
            assertEquals(expected, users);
        }

        @Test
        @Description("Should return list of three users")
        @DatabaseSetup("classpath:/dbunit/UserServiceTest/getUsers/shouldReturnListOfThreeUsers.xml")
        public void shouldReturnListOfThreeUsers() {
            // given
            List<UserDto> expected = List.of(
                    new UserDto(1L, "admin", "admin@example.com", "password"),
                    new UserDto(2L, "user01", "user01@example.com", "password"),
                    new UserDto(3L, "user02", "user02@example.com", "password")
            );

            // when
            var users = userService.getUsers();

            // then
            assertEquals(3, users.size());
            assertEquals(expected, users);
        }

        @Test
        @Description("Should return empty list of users")
        @DatabaseSetup("classpath:/dbunit/UserServiceTest/getUsers/shouldReturnEmptyListOfUsers.xml")
        public void shouldReturnEmptyListOfUsers() {
            // given
            List<UserDto> expected = List.of();

            // when
            var users = userService.getUsers();

            // then
            assertEquals(0, users.size());
            assertEquals(expected, users);
        }
    }

    @Nested
    @Description("Get user by id")
    class GetUserById {

        @Test
        @Description("Should return user by id")
        @DatabaseSetup("classpath:/dbunit/UserServiceTest/getUserById/shouldReturnUserById.xml")
        public void shouldReturnUserById() {
            // given
            UserDto expected = new UserDto(1L, "admin", "admin@example.com", "password");

            // when
            var user = userService.getUserById(1L);

            // then
            assertEquals(expected, user);
        }

        @Test
        @Description("Should return null when user not found")
        @DatabaseSetup("classpath:/dbunit/UserServiceTest/getUserById/shouldReturnNullWhenUserNotFound.xml")
        public void shouldReturnNullWhenUserNotFound() {
            // when
            var user = userService.getUserById(4L);

            // then
            assertEquals(null, user);
        }

        @Test
        @Description("Should return null when id is null")
        public void shouldReturnNullWhenIdIsNull() {
            // when
            var user = userService.getUserById(null);

            // then
            assertEquals(null, user);
        }
    }

    @Nested
    @Description("Create user")
    class CreateUser {

        @Test
        @Description("Should create user")
        @DatabaseSetup("classpath:/dbunit/UserServiceTest/createUser/shouldCreateUser.xml")
        public void shouldCreateUser() {
            // given
            UserDto userDto = new UserDto(null, "admin", "admin@example.com", "password");
            UserDto expected = new UserDto(1L, "admin", "admin@example.com", "password");

            // when
            var user = userService.createUser(userDto);

            // then
            assertEquals(expected.getUsername(), user.getUsername());
            assertEquals(expected.getEmail(), user.getEmail());
            assertEquals(expected.getPassword(), user.getPassword());
        }
    }

    @Nested
    @Description("Update user")
    class UpdateUser {

        @Test
        @Description("Should update user")
        @DatabaseSetup("classpath:/dbunit/UserServiceTest/updateUser/shouldUpdateUser.xml")
        public void shouldUpdateUser() {
            // given
            UserDto userDto = new UserDto(null, "admin1", "admin1@example.com", "password1");
            UserDto expected = new UserDto(1L, "admin1", "admin1@example.com", "password1");

            // when
            var user = userService.updateUser(1L, userDto);

            // then
            assertEquals(expected, user);
        }

        @Test
        @Description("Should return null when user not found")
        @DatabaseSetup("classpath:/dbunit/UserServiceTest/updateUser/shouldReturnNullWhenUserNotFound.xml")
        public void shouldReturnNullWhenUserNotFound() {
            // given
            UserDto userDto = new UserDto(null, "admin1", "admin1@example.com", "password1");

            // when
            var user = userService.updateUser(4L, userDto);

            // then
            assertEquals(null, user);
        }

        @Test
        @Description("Should return null when id is null")
        public void shouldReturnNullWhenIdIsNull() {
            // given
            UserDto userDto = new UserDto(null, "admin1", "admin1@example.com", "password1");

            // when
            var user = userService.updateUser(null, userDto);

            // then
            assertEquals(null, user);
        }

        @Test
        @Description("Should return null when userDto is null")
        public void shouldReturnNullWhenUserDtoIsNull() {
            // when
            var user = userService.updateUser(1L, null);

            // then
            assertEquals(null, user);
        }
    }

    @Nested
    @Description("Delete user")
    class DeleteUser {

        @Test
        @Description("Should delete user")
        @DatabaseSetup("classpath:/dbunit/UserServiceTest/deleteUser/shouldDeleteUser.xml")
        public void shouldDeleteUser() {
            // when
            var result = userService.deleteUser(1L);

            // then
            assertEquals(true, result);
        }

        @Test
        @Description("Should return false when user not found")
        @DatabaseSetup("classpath:/dbunit/UserServiceTest/deleteUser/shouldReturnFalseWhenUserNotFound.xml")
        public void shouldReturnFalseWhenUserNotFound() {
            // when
            var result = userService.deleteUser(4L);

            // then
            assertEquals(false, result);
        }

        @Test
        @Description("Should return false when id is null")
        public void shouldReturnFalseWhenIdIsNull() {
            // when
            try {
                userService.deleteUser(null);
            } catch (Exception e) {
                // then
                assertEquals("The given id must not be null", e.getMessage());
            }
        }
    }
}