package com.imooc.product.service.impl;

import com.imooc.product.dataobject.ProductInfo;
import com.imooc.product.enums.ProductStatusEnum;
import com.imooc.product.repository.ProductInfoRepository;
import com.imooc.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created on 2019/6/13.
 *
 * @author Grak
 * @since 1.0
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductInfoRepository infoRepository;

    @Override
    public List<ProductInfo> findByProductStatus(Integer productStatus) {
        return infoRepository.findByProductStatus(productStatus);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return infoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }
}
