package com.kunlun.api.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by kunlun
 * @version <0.1>
 * @created on 2018/1/2.
 */
@RestController
public class IndexController {

    @Value("${spring.datasource.url}")
    String datasourceUrl;

    @GetMapping("/index/datasource")
    public String datasource() {
        return "数据库连接地址为：" + datasourceUrl;
    }
}
