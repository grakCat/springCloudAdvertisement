/*
 * Copyright (c) 2017. Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 */

package com.stude.springbootdemo.xlsx;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * 样本相关配置信息
 * </p>
 * <p>
 * Created on 2017/3/15.
 *
 * @author Alan
 * @since 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "sample")
public class SampleConfig {
    public String samplePackage;

    public String samplePath;

    public String getSamplePackage() {
        return samplePackage;
    }

    public void setSamplePackage(String samplePackage) {
        this.samplePackage = samplePackage;
    }

    public String getSamplePath() {
        return samplePath;
    }

    public void setSamplePath(String samplePath) {
        this.samplePath = samplePath;
    }
}
