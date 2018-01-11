package com.kunlun.api.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kunlun.entity.GoodExt;
import com.kunlun.entity.MallImg;
import com.kunlun.result.DataRet;
import com.kunlun.result.PageResult;
import com.kunlun.api.service.SellerGoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author JackSpeed
 * @version V1.0 <>
 * @date 17-12-21下午12:54
 * @desc
 */
@RestController
@RequestMapping("seller/good")
public class SellerGoodController {

    @Autowired
    private SellerGoodService sellerGoodService;

    /**
     * 创建商品
     *
     * @param object JSONObject
     * @return DataRet
     */
    @PostMapping(value = "/add")
    public DataRet add(@RequestBody JSONObject object) {
        GoodExt goods = object.getObject("goods", GoodExt.class);
        List<MallImg> imgList = object.getJSONArray("imageList").toJavaList(MallImg.class);
        goods.setImgList(imgList);
        return sellerGoodService.add(goods);
    }

    /**
     * 修改商品
     *
     * @param object
     * @return DataRet
     */
    @PostMapping(value = "/update")
    public DataRet updateGood(@RequestBody JSONObject object) {
        GoodExt goods = object.getObject("goods", GoodExt.class);
        if (object.containsKey("imageList")) {
            List<MallImg> imageList = object.getJSONArray("imageList").toJavaList(MallImg.class);
            goods.setImgList(imageList);
        }
        return sellerGoodService.updateGood(goods);
    }

    /**
     * 根据id删除商品
     *
     * @param id
     * @return
     */
    @PostMapping("/delete")
    public DataRet delete(@RequestParam(value = "id") Long id){
        return sellerGoodService.delete(id);
    }


    /**
     * 批量删除商品
     *
     * @return DataRet
     */
    @PostMapping(value = "/deleteByIdList")
    public DataRet deleteByIdList(@RequestBody JSONArray jsonObject) {
        List<Long> idList = jsonObject.toJavaList(Long.class);
        return sellerGoodService.deleteByIdList(idList);
    }

    /**
     * 根据商品id查询商品
     *
     * @param id 商品id
     * @return DataRet
     */
    @GetMapping(value = "/findById")
    public DataRet findById(@RequestParam(value = "id") Long id) {
        return sellerGoodService.findById(id);
    }

    /**
     * 条件查询商品列表
     *
     * @param pageNo     Integer
     * @param pageSize   Integer
     * @param sellerId     Long
     * @param type       UNBIND_CATEGORY 未绑定类目
     *                   UNBIND_ACTIVITY 未绑定活动
     *                   BIND_ACTIVITY 已经绑定活动
     * @param searchKey  String
     * @param goodNo     String
     * @param startDate  String
     * @param endDate    String
     * @param brandId    Long
     * @param saleStatus String
     * @param categoryId Long
     * @param hot        String
     * @param isNew      String
     * @param freight    String
     * @return List
     */
    @PostMapping(value = "/findByCondition")
    public PageResult findByCondition(@RequestParam(value = "pageNo") Integer pageNo,
                                      @RequestParam(value = "pageSize") Integer pageSize,
                                      @RequestParam(value = "sellerId") Long sellerId,
                                      @RequestParam(value = "type") String type,
                                      @RequestParam(value = "searchKey", required = false) String searchKey,
                                      @RequestParam(value = "goodNo", required = false) String goodNo,
                                      @RequestParam(value = "startDate", required = false) String startDate,
                                      @RequestParam(value = "endDate", required = false) String endDate,
                                      @RequestParam(value = "brandId", required = false) Long brandId,
                                      @RequestParam(value = "saleStatus", required = false) String saleStatus,
                                      @RequestParam(value = "categoryId", required = false) Long categoryId,
                                      @RequestParam(value = "hot", required = false) String hot,
                                      @RequestParam(value = "isNew", required = false) String isNew,
                                      @RequestParam(value = "freight", required = false) String freight) {
        return sellerGoodService.findByCondition(pageNo, pageSize, sellerId, type, searchKey, goodNo,
                startDate, endDate, brandId, saleStatus, categoryId, hot, isNew, freight);
    }

    /**
     * 批量商品上下架
     *
     * @param object JSONObject
     * @return DataRet
     */
    @PostMapping(value = "/batchUpdateSaleStatus")
    public DataRet batchUpdateSaleStatus(@RequestBody JSONObject object) {
        //ON_SALE上架 OFF_SALE下架 默认上架
        String saleStatus = object.getString("saleStatus");
        //商品id集合
        List<Long> goodsIdList = object.getJSONArray("goodIdList").toJavaList(Long.class);
        return sellerGoodService.batchUpdateSaleStatus(saleStatus, goodsIdList);
    }

    /**
     * 商品库存修改
     *
     * @param count 数量  小于0 扣减，大于0 增加库存
     * @param id    商品id，主键
     * @return DataRet
     */
    @PostMapping(value = "/updateGoodStock")
    public DataRet<String> updateGoodStock(@RequestParam(value = "id") Long id,
                                   @RequestParam(value = "count") Integer count) {
        return sellerGoodService.updateGoodStock(id, count);
    }

}
