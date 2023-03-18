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
public class UserOrderTest {

    @Test
    public void testUserOrderModel() {

        UserOrder userOrder = MockData.buildMockUserOrder();
        userOrder.setId(1L);
        userOrder.setUser(new User());
        userOrder.setItems(Collections.emptyList());
        userOrder.setTotal(BigDecimal.ONE);
        userOrder.getId();
        userOrder.getUser();
        userOrder.getItems();
        userOrder.getTotal();
    }

    @Test
    public void testUserOrderWithoutCart() {

        UserOrder userOrder = UserOrder.createFromCart(null);
        assertEquals(new UserOrder(), userOrder);
    }

    @Test
    public void testUserOrderWithCart() {

        Cart cart = MockData.buildMockCart(new User());
        UserOrder userOrder = UserOrder.createFromCart(cart);
        assertEquals(cart.getItems(), userOrder.getItems());
        assertEquals(cart.getUser(), userOrder.getUser());
        assertEquals(cart.getTotal(), userOrder.getTotal());
    }
}
