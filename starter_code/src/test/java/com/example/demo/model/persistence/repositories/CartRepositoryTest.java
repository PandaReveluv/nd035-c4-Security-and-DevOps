package com.example.demo.model.repositories;

import com.example.demo.MockData;
import com.example.demo.SareetaApplication;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CartRepositoryTest {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByUser() {

        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        Cart cart = new Cart();
        cart.addItem(MockData.buildMockItem());

        user.setCart(cart);
        cart.setUser(user);

        userRepository.save(user);
        cartRepository.save(cart);

        Cart result = cartRepository.findByUser(user);
        assertEquals(result.getUser(), user);
    }
}
