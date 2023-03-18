package com.example.demo.controllers;

import com.example.demo.MockData;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartControllerTest {

    @InjectMocks
    private CartController cartController;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CartRepository cartRepository;
    @Mock
    private ItemRepository itemRepository;

    @Test
    public void testAddToCartWithoutUser() {

        when(userRepository.findByUsername(any())).thenReturn(null);
        ResponseEntity<Cart> result = cartController.addTocart(new ModifyCartRequest());
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void testAddToCartWithoutItem() {

        when(userRepository.findByUsername(any())).thenReturn(MockData.buildMockUser(null));
        when(itemRepository.findById(any())).thenReturn(Optional.empty());
        ResponseEntity<Cart> result = cartController.addTocart(new ModifyCartRequest());
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void testAddToCartWithItem() {

        when(userRepository.findByUsername(any())).thenReturn(MockData.buildMockUser(MockData.buildMockCart(new User())));
        when(itemRepository.findById(any())).thenReturn(Optional.of(MockData.buildMockItem()));
        ResponseEntity<Cart> result = cartController.addTocart(new ModifyCartRequest());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testRemoveFromCartWithoutUser() {

        when(userRepository.findByUsername(any())).thenReturn(null);
        ResponseEntity<Cart> result = cartController.removeFromcart(new ModifyCartRequest());
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void testRemoveFromCartWithoutItem() {

        when(userRepository.findByUsername(any())).thenReturn(MockData.buildMockUser(null));
        when(itemRepository.findById(any())).thenReturn(Optional.empty());
        ResponseEntity<Cart> result = cartController.removeFromcart(new ModifyCartRequest());
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void testRemoveFromCartWithItem() {

        when(userRepository.findByUsername(any())).thenReturn(MockData.buildMockUser(MockData.buildMockCart(new User())));
        when(itemRepository.findById(any())).thenReturn(Optional.of(MockData.buildMockItem()));
        ResponseEntity<Cart> result = cartController.removeFromcart(new ModifyCartRequest());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}
