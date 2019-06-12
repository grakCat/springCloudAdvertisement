package com.imooc.product.service.impl;

import com.imooc.product.dataobject.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created on 2019/6/13.
 *
 * @author Grak
 * @since 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {

    @Autowired
    ProductServiceImpl productService;

    @Test
    public void findByProductStatus() {
        List<ProductInfo> productInfos = productService.findByProductStatus(0);
        Assert.assertTrue(productInfos.size() > 0);
    }
}