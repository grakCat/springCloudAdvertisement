package com.stude.springbootdemo.controller;

import com.stude.springbootdemo.comfig.OtherPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 2019/5/17.
 *
 * @author hy
 * @since 1.0
 */
@RestController
public class OPath {

    @Autowired
    OtherPath otherPath;

    @RequestMapping("/index")
    public String tree(){
        return otherPath.toString();
    }
}
