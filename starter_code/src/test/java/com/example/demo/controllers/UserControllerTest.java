package com.example.demo.controllers;

import com.example.demo.MockData;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    public void testFindById() {

        when(userRepository.findById(any())).thenReturn(Optional.of(MockData.buildMockUser(null)));
        ResponseEntity<User> result = userController.findById(1L);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testFindByUserNameWithoutUser() {

        when(userRepository.findByUsername(any())).thenReturn(null);
        ResponseEntity<User> result = userController.findByUserName("username");
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void testFindByUserNameWithUser() {

        when(userRepository.findByUsername(any())).thenReturn(MockData.buildMockUser(null));
        ResponseEntity<User> result = userController.findByUserName("username");
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testCreateUserWithPasswordLessThan7Character() {

        String passwordLessThan7Character = "123";
        ResponseEntity<User> result = userController.createUser(new CreateUserRequest("username", passwordLessThan7Character, passwordLessThan7Character));
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    public void testCreateUserWithPasswordIsNotEqualToConfirmPassword() {

        String password = "somePassword";
        String confirmPassword = "anotherPassword";
        ResponseEntity<User> result = userController.createUser(new CreateUserRequest("username", password, confirmPassword));
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    public void testCreateUser() {

        String password = "somePassword";
        ResponseEntity<User> result = userController.createUser(new CreateUserRequest("username", password, password));
        when(bCryptPasswordEncoder.encode(any())).thenReturn("encodedPassword");
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}
