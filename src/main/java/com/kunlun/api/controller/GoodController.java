package com.kunlun.api.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kunlun.entity.Good;
import com.kunlun.api.service.GoodService;
import com.kunlun.entity.GoodExt;
import com.kunlun.entity.MallImg;
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
     * @param jsonObject
     * @return
     */
    @PostMapping("/add")
    public DataRet<String> add(@RequestBody JSONObject jsonObject) {
        GoodExt good = jsonObject.getObject("good", GoodExt.class);
        List<MallImg> imgList = jsonObject.getJSONArray("imageList").toJavaList(MallImg.class);
        good.setImgList(imgList);
        return goodService.add(good);
    }


    /**
     * 获取商品详情
     *
     * @param id
     * @return
     */
    @GetMapping("/findById")
    public DataRet<GoodExt> findById(@RequestParam(value = "id") Long id) {
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
    public PageResult findByCondition(@RequestParam(value = "pageNo") Integer pageNo,
                                      @RequestParam(value = "pageSize") Integer pageSize,
                                      @RequestParam(value = "sellerId") Long sellerId,
                                      @RequestParam(value = "type") String type,
                                      @RequestParam(value = "searchKey", required = false) String searchKey,
                                      @RequestParam(value = "goodNo", required = false) String goodNo,
                                      @RequestParam(value = "startDate", required = false) Date startDate,
                                      @RequestParam(value = "endDate", required = false) Date endDate,
                                      @RequestParam(value = "brandId", required = false) Long brandId,
                                      @RequestParam(value = "onSale", required = false) String onSale,
                                      @RequestParam(value = "categoryId", required = false) Long categoryId,
                                      @RequestParam(value = "hot", required = false) String hot,
                                      @RequestParam(value = "isNew", required = false) String isNew,
                                      @RequestParam(value = "freight", required = false) String freight) {
        return goodService.findByCondition(pageNo, pageSize, searchKey, goodNo, startDate, endDate,
                brandId, onSale, categoryId, hot, isNew, freight, sellerId, type);
    }

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    @PostMapping("/deleteById")
    public DataRet<String> deleteById(@RequestParam(value = "id") Long id) {
        return goodService.deleteById(id);
    }


    /**
     * 批量删除
     *
     * @param idList
     * @return
     */
    @PostMapping("/deleteByIdList")
    public DataRet<String> deleteByIdList(@RequestBody List<Long> idList) {
        return goodService.deleteByIdList(idList);
    }


    /**
     * 修改商品
     *
     * @param jsonObject
     * @return
     */
    @PostMapping("/update")
    public DataRet<String> update(@RequestBody JSONObject jsonObject) {
        GoodExt good = jsonObject.getObject("good", GoodExt.class);
        if (jsonObject.containsKey("imageList")){
            List<MallImg> imgList = jsonObject.getJSONArray("imageList").toJavaList(MallImg.class);
            good.setImgList(imgList);
        }
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
    public DataRet<String> updateSaleStatus(@RequestParam(value = "onSale") String onSale,
                                            @RequestParam(value = "id") Long id) {
        return goodService.updateSaleStatus(onSale, id);
    }


    /**
     * 商品批量上下
     *
     * @param jsonObject
     * @return
     */
    @PostMapping("/updateSaleList")
    public DataRet<String> updateSaleList(@RequestBody JSONObject jsonObject) {
        String onSale = jsonObject.getString("onSale");
        List<Long> goodIdList = jsonObject.getJSONArray("goodIdList").toJavaList(Long.class);
        return goodService.updateSaleList(onSale, goodIdList);
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
    public DataRet<String> audit(@RequestParam(value = "audit") String audit,
                                 @RequestParam(value = "reason") String reason,
                                 @RequestParam(value = "id") Long id) {
        return goodService.audit(audit, reason, id);
    }


    /**
     * 修改商品库存
     *
     * @param id
     * @param count
     * @return
     */
    @PostMapping("/updateStock")
    public DataRet<String> updateStock(@RequestParam(value = "id") Long id,
                                       @RequestParam(value = "count") Integer count) {
        return goodService.updateStock(id, count);
    }

    /**
     * 修改商品库存
     *
     * @param jsonArray JSONArray
     * @return
     */
    @PostMapping("/updateStocks")
    public DataRet<String> updateStocks(@RequestBody JSONArray jsonArray) {
        List<Good> goodList = jsonArray.toJavaList(Good.class);
        return goodService.updateStocks(goodList);
    }

    /**
     * 商品信息校验
     *
     * @param goodId
     * @return
     */
    @GetMapping("/checkGood")
    public DataRet<Good> checkGood(@RequestParam(value = "goodId") Long goodId,
                                   @RequestParam(value = "count") Integer count,
                                   @RequestParam(value = "orderFee") Integer orderFee) {
        return goodService.checkGood(goodId, count, orderFee);
    }


    /**
     * 商品销量
     *
     * @param count
     * @param goodId
     * @return
     */
    @PostMapping("/backstage/good/updateSaleVolume")
    public DataRet<String> updateSaleVolume(@RequestParam(value = "count") Integer count,
                                     @RequestParam(value = "goodId") Long goodId){
        return goodService.updateSaleVolume(count,goodId);
    }

}
