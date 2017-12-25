package com.kunlun.good.controller;

import com.kunlun.good.service.WxGoodService;
import com.kunlun.result.DataRet;
import com.kunlun.result.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author JackSpeed
 * @version V1.0 <>
 * @date 17-12-22下午3:29
 * @desc 微信小程序商品模块
 */
@RestController
@RequestMapping("wx/good")
public class WxGoodController {


    @Autowired
    private WxGoodService wxGoodService;


    /**
     * 商品详情
     *
     * @param id Long
     * @return Good
     */
    @GetMapping(value = "findById")
    public DataRet findById(@RequestParam(value = "id") Long id) {
        return wxGoodService.findById(id);
    }


    /**
     * 查询商品评价列表
     *
     * @param pageNo   Integer
     * @param pageSize Integer
     * @param goodId   Long
     * @return List
     */
    @GetMapping(value = "findEstimateList")
    public PageResult findEstimateList(@RequestParam(value = "pageNo") Integer pageNo,
                                       @RequestParam(value = "pageSize") Integer pageSize,
                                       @RequestParam(value = "goodId") Long goodId) {
        return wxGoodService.findEstimateList(pageNo, pageSize, goodId);
    }


    /**
     * 商品搜索
     *
     * @param pageNo     Integer
     * @param pageSize   Integer
     * @param categoryId Long
     * @param searchKey  String
     * @return List
     */
    @GetMapping(value = "findByCondition")
    public PageResult findByCondition(@RequestParam(value = "pageNo") Integer pageNo,
                                      @RequestParam(value = "pageSize") Integer pageSize,
                                      @RequestParam(value = "categoryId", required = false) Long categoryId,
                                      @RequestParam(value = "searchKey", required = false) String searchKey) {
        return wxGoodService.findByCondition(pageNo, pageSize, categoryId, searchKey);
    }


    /**
     * 获取商品快照详情
     *
     * @param orderId Long
     * @return DataRet
     */
    @GetMapping(value = "findGoodSnapshot")
    public DataRet findGoodSnapshot(@RequestParam("orderId") Long orderId) {
        return wxGoodService.findGoodSnapshot(orderId);
    }
}
