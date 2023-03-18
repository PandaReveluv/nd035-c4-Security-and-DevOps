package com.example.demo.model.persistence;

import com.example.demo.MockData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartTest {

    @Test
    public void testCartModel() {
        Cart cart = MockData.buildMockCart(null);
        cart.setId(1L);
        cart.setItems(Collections.singletonList(MockData.buildMockItem()));
        cart.setTotal(BigDecimal.ONE);
        cart.setUser(MockData.buildMockUser(null));
        cart.getId();
        cart.getUser();
        cart.getTotal();
        cart.getItems();
    }

    @Test
    public void testAddNullItem() {

        Cart cart = new Cart();
        cart.addItem(null);
        assertEquals(Collections.emptyList(), cart.getItems());
        assertEquals(new BigDecimal(0), cart.getTotal());
    }

    @Test
    public void testAddItem() {

        Cart cart = new Cart();
        cart.addItem(MockData.buildMockItem());
        assertEquals(Collections.singletonList(MockData.buildMockItem()), cart.getItems());
        assertEquals(MockData.buildMockItem().getPrice(), cart.getTotal());
    }

    @Test
    public void testRemoveNullItem() {

        Cart cart = new Cart();
        cart.removeItem(null);
        assertEquals(Collections.emptyList(), cart.getItems());
        assertEquals(new BigDecimal(0), cart.getTotal());
    }

    @Test
    public void testRemoveItem() {

        Cart cart = new Cart();
        cart.addItem(MockData.buildMockItem());
        cart.removeItem(MockData.buildMockItem());
        assertEquals(Collections.emptyList(), cart.getItems());
        assertEquals(new BigDecimal(0), cart.getTotal());
    }
}
