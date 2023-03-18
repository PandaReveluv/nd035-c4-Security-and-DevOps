package com.example.demo.model.persistence;

import com.example.demo.MockData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

    @Test
    public void testUserModel() {
        User user = MockData.buildMockUser(new Cart());
        user.setId(1L);
        user.setCart(new Cart());
        user.setUsername("username");
        user.setPassword("password");
        user.getId();
        user.getCart();
        user.getUsername();
        user.getPassword();
    }
}
