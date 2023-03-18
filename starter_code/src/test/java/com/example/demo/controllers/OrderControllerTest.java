package com.example.demo.controllers;

import com.example.demo.MockData;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderRepository orderRepository;

    @Test
    public void testSubmitWithoutUser() {

        when(userRepository.findByUsername(any())).thenReturn(null);
        ResponseEntity<UserOrder> result = orderController.submit("username");
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void testSubmitWithUser() {

        when(userRepository.findByUsername(any())).thenReturn(MockData.buildMockUser(MockData.buildMockCart(new User())));
        ResponseEntity<UserOrder> result = orderController.submit("username");
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testGetOrdersForUserWithoutUser() {

        when(userRepository.findByUsername(any())).thenReturn(null);
        ResponseEntity<List<UserOrder>> result = orderController.getOrdersForUser("username");
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void testGetOrdersForUserWithUser() {

        when(userRepository.findByUsername(any())).thenReturn(MockData.buildMockUser(null));
        when(orderRepository.findByUser(any())).thenReturn(Collections.singletonList(MockData.buildMockUserOrder()));
        ResponseEntity<List<UserOrder>> result = orderController.getOrdersForUser("username");
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}
