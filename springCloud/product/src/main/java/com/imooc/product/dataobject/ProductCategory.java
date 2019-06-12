package com.imooc.product.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created on 2019/6/13.
 *
 * @author Grak
 * @since 1.0
 */
@Data
@Entity
public class ProductCategory {

    @Id //表明是id
    @GeneratedValue //表明属性自增
    private Integer categoryId;

    /** 类目名字. */
    private String categoryName;

    /** 类目编号. */
    private Integer categoryType;

    private Date createTime;

    private Date updateTime;
}
