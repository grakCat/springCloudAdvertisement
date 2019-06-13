package com.imooc.product.service;

import com.imooc.product.dataobject.ProductInfo;

import java.util.List;

/**
 * Created on 2019/6/13.
 *
 * @author Grak
 * @since 1.0
 */
public interface ProductService {

    List<ProductInfo> findByProductStatus(Integer productStatus);

    List<ProductInfo> findUpAll();
}
