package com.imooc.product.controller;

import com.imooc.product.VO.ProductVO;
import com.imooc.product.VO.ResultVO;
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
public class ProductControllerTest {

    @Autowired
    ProductController controller;

    @Test
    public void list() {
        ResultVO<ProductVO> resultVO = controller.list();
        Assert.assertTrue(resultVO.getCode() == 0);
    }
}