package com.kunlun.seller;


import com.kunlun.entity.GoodExt;
import com.kunlun.entity.GoodRequestParams;
import com.kunlun.result.DataRet;
import com.kunlun.result.PageResult;

import java.util.Date;
import java.util.List;

/**
 * @author JackSpeed
 * @version V1.0 <>
 * @date 17-12-21下午12:55
 * @desc 商家商品模块
 */
public interface SellerGoodService {
    /**
     * 创建商品
     *
     * @param goodExt GoodExt
     * @return
     */
    DataRet add(GoodExt goodExt);


    /**
     * 根据id查询商品详情
     *
     * @param goodId
     * @return
     */
    DataRet findById(Long goodId);

    /**
     * 根据id修改商品信息
     *
     * @param goodExt
     * @return
     */
    DataRet updateGood(GoodExt goodExt);

    /**
     * 条件查询商品列表
     *
     * @param pageNo
     * @param pageSize
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
    PageResult findByCondition(Integer pageNo,
                               Integer pageSize,
                               Long userId,
                               String type,
                               String searchKey,
                               String goodNo,
                               String startDate,
                               String endDate,
                               Long brandId,
                               String onSale,
                               Long categoryId,
                               String hot,
                               String isNew,
                               String freight);

    /**
     * 批量删除商品
     *
     * @param idList 商品id集合
     * @return
     */
    DataRet deleteByIdList(List<Long> idList);

    /**
     * 商品库存减扣
     *
     * @param id    商品id--主键
     * @param count 扣减、增加数量
     * @return DataRet
     */
    DataRet updateGoodStock(Long id, Integer count);

    /**
     * 批量商品上下架
     *
     * @param saleStatus 1上架 0下架 默认上架
     * @param goodIdList 商品id集合
     * @return
     */
    DataRet batchUpdateSaleStatus(String saleStatus, List<Long> goodIdList);
}
