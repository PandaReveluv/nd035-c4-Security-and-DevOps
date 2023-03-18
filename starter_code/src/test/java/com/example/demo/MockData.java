package com.example.demo;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import java.math.BigDecimal;
import java.util.Collections;

public class MockData {

    public static User buildMockUser(Cart cart) {
        return new User(1L, "username", "password", cart);
    }

    public static Item buildMockItem() {
        return new Item(1L, "item", BigDecimal.ONE, "description");
    }

    public static Cart buildMockCart(User user) {
        return new Cart(1L, Collections.singletonList(buildMockItem()), user, BigDecimal.ONE);
    }

    public static UserOrder buildMockUserOrder() {
        return new UserOrder(1L, Collections.singletonList(buildMockItem()), buildMockUser(null), BigDecimal.ONE);
    }
}
