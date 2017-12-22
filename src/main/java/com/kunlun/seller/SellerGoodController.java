package com.kunlun.seller;

import com.alibaba.fastjson.JSONObject;
import com.kunlun.entity.GoodExt;
import com.kunlun.entity.MallImage;
import com.kunlun.result.DataRet;
import com.kunlun.result.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author JackSpeed
 * @version V1.0 <>
 * @date 17-12-21下午12:54
 * @desc
 */
@RestController
@RequestMapping("good")
public class SellerGoodController {

    @Autowired
    private SellerGoodService sellerGoodService;

    /**
     * 创建商品
     *
     * @param object
     * @return
     */
    @PostMapping(value = "/add")
    public DataRet add(@RequestBody JSONObject object) {
        GoodExt goods = object.getObject("good", GoodExt.class);
        List<MallImage> imgList = object.getJSONArray("imageList").toJavaList(MallImage.class);
        goods.setImgList(imgList);
        return sellerGoodService.add(goods);
    }

    /**
     * 修改商品
     *
     * @param object
     * @return
     */
    @PostMapping(value = "/update")
    public DataRet updateGood(@RequestBody JSONObject object) {
        GoodExt goods = object.getObject("good", GoodExt.class);
        if (object.containsKey("imageList")) {
            List<MallImage> imageList = object.getJSONArray("imageList").toJavaList(MallImage.class);
            goods.setImgList(imageList);
        }
        return sellerGoodService.updateGood(goods);
    }


    /**
     * 批量删除商品
     *
     * @return
     */
    @PostMapping(value = "/deleteByIdList")
    public DataRet deleteByIdList(@RequestBody JSONObject jsonObject) {
        List<Long> idList = jsonObject.getJSONArray("idList").toJavaList(Long.class);
        return sellerGoodService.deleteByIdList(idList);
    }

    /**
     * 根据商品id查询商品
     *
     * @param id 商品id
     * @return
     */
    @GetMapping(value = "/findById")
    public DataRet findById(@RequestParam(value = "id") Long id) {
        return sellerGoodService.findById(id);
    }


    /**
     * 条件查询商品列表
     *
     * @param pageNo
     * @param pageSize
     * @param userId
     * @param searchKey
     * @param goodNo
     * @param startDate
     * @param endDate
     * @param brandId
     * @param onSale
     * @param categoryId
     * @param hot
     * @param isNew
     * @param freight
     * @return
     */
    @PostMapping(value = "/findByCondition")
    public PageResult findByCondition(@RequestParam(value = "pageNo") Integer pageNo,
                                      @RequestParam(value = "pageSize") Integer pageSize,
                                      @RequestParam(value = "userId") Long userId,
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
        return sellerGoodService.findByCondition(pageNo, pageSize, userId, type, searchKey, goodNo,
                startDate, endDate, brandId, saleStatus, categoryId, hot, isNew, freight);
    }

    /**
     * 批量商品上下架
     *
     * @param object
     * @return
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
     * @return
     */
    @GetMapping(value = "/updateGoodStock/{id}/{count}")
    public DataRet updateGoodStock(@PathVariable("id") Long id,
                                   @PathVariable("count") Integer count) {
        return sellerGoodService.updateGoodStock(id, count);
    }
}
