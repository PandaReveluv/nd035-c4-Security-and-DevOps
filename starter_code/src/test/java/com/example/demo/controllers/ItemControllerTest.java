package com.example.demo.controllers;

import com.example.demo.MockData;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
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
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemControllerTest {

    @InjectMocks
    private ItemController itemController;

    @Mock
    private ItemRepository itemRepository;

    @Test
    public void testGetItems() {

        when(itemRepository.findAll()).thenReturn(Collections.singletonList(MockData.buildMockItem()));
        ResponseEntity<List<Item>> result = itemController.getItems();
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testGetItemById() {

        when(itemRepository.findById(any())).thenReturn(Optional.of(MockData.buildMockItem()));
        ResponseEntity<Item> result = itemController.getItemById(1L);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testGetItemByName() {

        when(itemRepository.findByName(any())).thenReturn(Collections.singletonList(MockData.buildMockItem()));
        ResponseEntity<List<Item>> result = itemController.getItemsByName("itemName");
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}
