package com.imooc.order.server.impl;

import com.imooc.order.dto.OrderDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created on 2019/6/13.
 *
 * @author Grak
 * @since 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceImplTest {

    @Autowired
    OrderServiceImpl orderService;

    @Test
    public void create() {
        OrderDTO order = new OrderDTO();
        order.setBuyerName("李逵");
        order.setBuyerPhone("68629315");
        order.setBuyerAddress("梁山");
        order.setBuyerOpenid("openId123456");

        OrderDTO orderSave = orderService.create(order);
        Assert.assertTrue(orderSave.getOrderId().length() > 4);
    }
}