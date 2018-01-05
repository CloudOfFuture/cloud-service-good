package com.kunlun.api.service;

import com.kunlun.entity.Good;
import com.kunlun.entity.GoodExt;
import com.kunlun.result.DataRet;
import com.kunlun.result.PageResult;

import java.util.Date;
import java.util.List;

/**
 * @author by hmy
 * @version <0.1>
 * @created on 2017-12-21.
 */
public interface GoodService {

    /**
     * 创建商品
     *
     * @param good
     * @return
     */
    DataRet<String> add(GoodExt good);

    /**
     * 获取详情
     *
     * @param id
     * @return
     */
    DataRet<GoodExt> findById(Long id);


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
     * @param onSale
     * @param categoryId
     * @param hot
     * @param isNew
     * @param freight
     * @return
     */
    PageResult findByCondition(Integer pageNo, Integer pageSize, String searchKey, String goodNo,
                               Date startDate, Date endDate, Long brandId, String onSale, Long categoryId,
                               String hot, String isNew, String freight, Long sellerId, String type);


    /**
     * 根据id删除商品
     *
     * @param id
     * @return
     */
    DataRet<String> deleteById(Long id);


    /**
     * 批量删除
     *
     * @param idList
     * @return
     */
    DataRet<String> deleteByIdList(List<Long> idList);


    /**
     * 修改商品信息
     *
     * @param good
     * @return
     */
    DataRet<String> update(GoodExt good);


    /**
     * 商品上下架
     *
     * @param onSale
     * @param id
     * @return
     */
    DataRet<String> updateSaleStatus(String onSale, Long id);


    /**
     * 商品批量上下架
     *
     * @param onSale
     * @param goodIdList
     * @return
     */
    DataRet<String> updateSaleList(String onSale, List<Long> goodIdList);


    /**
     * 新建商品审核
     *
     * @param audit
     * @param reason
     * @param id
     * @return
     */
    DataRet<String> audit(String audit, String reason, Long id);


    /**
     * 修改商品库存
     *
     * @param id
     * @param count
     * @return
     */
    DataRet<String> updateStock(Long id, Integer count);

    /**
     * 商品检查
     *
     * @param goodId
     * @param count
     * @param orderFee
     * @return
     */
    DataRet<Good> checkGood(Long goodId, Integer count, Integer orderFee);

    /**
     * 修改商品库存
     *
     * @param goodList List
     * @return DataRet
     */
    DataRet<String> updateStocks(List<Good> goodList);


    /**
     * 商品销量
     *
     * @param count
     * @param goodId
     * @return
     */
    DataRet<String> updateSaleVolume(Integer count, Long goodId);
}
