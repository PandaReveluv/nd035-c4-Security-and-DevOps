package com.example.demo.model.persistence;

import com.example.demo.MockData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemTest {

    @Test
    public void testItemModel() {
        Item item = MockData.buildMockItem();
        item.setId(1L);
        item.setName("item");
        item.setDescription("description");
        item.setPrice(BigDecimal.ONE);
        item.getId();
        item.getName();
        item.getDescription();
        item.getPrice();
        item.equals(MockData.buildMockItem());
        item.hashCode();
    }
}
