package com.kunlun.good;

import com.kunlun.entity.Good;
import com.kunlun.result.DataRet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



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


    /**
     * 获取商品详情
     *
     * @param id
     * @return
     */
    @GetMapping("/findById")
    public DataRet<Good> findById(@RequestParam(value = "id") Long id){
        return goodService.findById(id);
    }
}
