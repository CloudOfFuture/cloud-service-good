package com.kunlun.good.controller;

import com.alibaba.fastjson.JSONObject;
import com.kunlun.entity.Good;
import com.kunlun.good.service.GoodService;
import com.kunlun.result.DataRet;
import com.kunlun.result.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
    public DataRet<String> add(@RequestBody Good good) {
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
    public DataRet<Good> findById(@RequestParam(value = "id") Long id) {
        return goodService.findById(id);
    }


    /**
     * 分页查询
     *
     * @param pageNo
     * @param pageSize
     * @param searchKey
     * @param goodNo
     * @param startDate
     * @param endDate
     * @param brandId
     * @param onSale     上下架 上下ON_SALE", "上架 OFF_SALE", "下架")
     * @param categoryId
     * @param hot        热销 是否热销 HOT", "热销 NOT_HOT", "非热销
     * @param isNew      新品 是否新品 IS_NEW", "新品 NOT_NEW", "非新品
     * @param freight    包邮 是否包邮 FREIGHT包邮 UN_FREIGHT 不包邮
     * @return
     */
    @GetMapping("/findByCondition")
    public PageResult findByCondition(@RequestParam(value = "page_no") Integer pageNo,
                                      @RequestParam(value = "page_size") Integer pageSize,
                                      @RequestParam(value = "search_key",required = false) String searchKey,
                                      @RequestParam(value = "good_no",required = false) String goodNo,
                                      @RequestParam(value = "start_date",required = false) Date startDate,
                                      @RequestParam(value = "end_date",required = false) Date endDate,
                                      @RequestParam(value = "brand_id",required = false) Long brandId,
                                      @RequestParam(value = "on_sale",required = false) String onSale,
                                      @RequestParam(value = "category_id",required = false) Long categoryId,
                                      @RequestParam(value = "hot",required = false) String hot,
                                      @RequestParam(value = "is_new",required = false) String isNew,
                                      @RequestParam(value = "freight",required = false) String freight) {
        return goodService.findByCondition(pageNo, pageSize, searchKey, goodNo, startDate, endDate,
                brandId, onSale, categoryId, hot, isNew, freight);
    }

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    @PostMapping("/deleteById")
    public DataRet<String> deleteById(@RequestParam(value = "id") Long id){
        return goodService.deleteById(id);
    }


    /**
     * 批量删除
     *
     * @param object
     * @return
     */
    @PostMapping("/deleteByIdList")
    public DataRet<String> deleteByIdList(@RequestBody JSONObject object){
        List<Long>idList=object.getJSONArray("idList").toJavaList(Long.class);
        return goodService.deleteByIdList(idList);
    }


    /**
     * 修改商品
     *
     * @param good
     * @return
     */
    @PostMapping("/update")
    public DataRet<String> update(@RequestBody Good good){
        //TODO 图片
        return goodService.update(good);
    }


    /**
     * 商品上下架
     *
     * @param onSale
     * @param id
     * @return
     */
    @GetMapping("/updateSaleStatus")
    public DataRet<String> updateSaleStatus(@RequestParam("on_sale") String onSale,
                                            @RequestParam("id") Long id){
        return goodService.updateSaleStatus(onSale,id);
    }


    /**
     * 商品批量上下
     *
     * @param jsonObject
     * @return
     */
    @PostMapping("/updateSaleList")
    public DataRet<String> updateSaleList(@RequestBody JSONObject jsonObject){
        String onSale=jsonObject.getString("onSale");
        List<Long>goodIdList=jsonObject.getJSONArray("goodIdList").toJavaList(Long.class);
        return goodService.updateSaleList(onSale,goodIdList);
    }


    /**
     * 新建商品审核
     *
     * @param audit
     * @param reason
     * @param id
     * @return
     */
    @PostMapping("/audit")
    public DataRet<String> audit(@RequestParam("audit") String audit,
                                 @RequestParam("reason") String reason,
                                 @RequestParam("id") Long id){
        return goodService.audit(audit,reason,id);
    }


    /**
     * 修改商品库存
     *
     * @param id
     * @param count
     * @return
     */
    @PostMapping("/updateStock")
    public DataRet<String> update(@RequestParam("id") Long id,
                                  @RequestParam("count") Integer count){
        return goodService.updateStock(id,count);
    }
}
