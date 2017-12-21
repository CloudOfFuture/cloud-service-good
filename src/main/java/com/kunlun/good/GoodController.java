package com.kunlun.good;

import com.alibaba.fastjson.JSONObject;
import com.kunlun.entity.Good;
import com.kunlun.entity.MallImage;
import com.kunlun.result.DataRet;
import com.sun.xml.internal.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author by hmy
 * @version <0.1>
 * @created on 2017-12-21.
 */

@RestController
@RequestMapping("/backstage/good")
public class GoodController {

    @Autowired
    private GoodService goodService;


    /**
     * 创建商品
     *
     * @param good
     * @return
     */
    @PostMapping("/add")
    public DataRet<String> add(@RequestBody Good good){
        //TODO 添加轮播图
        return goodService.add(good);
    }

    @GetMapping("/findById")
    public DataRet<Good> findById(){
        return null;
    }
}
