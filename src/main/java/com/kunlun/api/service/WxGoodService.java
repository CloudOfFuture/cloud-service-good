package com.kunlun.api.service;

import com.kunlun.entity.GoodSnapshot;
import com.kunlun.result.DataRet;
import com.kunlun.result.PageResult;

/**
 * @author JackSpeed
 * @version V1.0 <>
 * @date 17-12-22下午3:32
 * @desc
 */
public interface WxGoodService {

    /**
     * 商品详情
     *
     * @param goodId Long
     * @return Good
     */
    DataRet findById(Long goodId);

    /**
     * 查询商品评价列表
     *
     * @param pageNo   Integer
     * @param pageSize Integer
     * @param goodId   Long
     * @return List
     */
    PageResult findEstimateList(Integer pageNo,
                                Integer pageSize,
                                Long goodId);

    /**
     * 商品搜索
     *
     * @param pageNo     Integer
     * @param pageSize   Integer
     * @param categoryId Long
     * @param searchKey  String
     * @return List
     */
    PageResult findByCondition(Integer pageNo,
                               Integer pageSize,
                               Long categoryId,
                               String searchKey);

    /**
     * 获取商品快照详情
     *
     * @param orderId Long
     * @return DataRet
     */
    DataRet findGoodSnapshot(Long orderId);

    /**
     * 新增商品快照
     * @param goodSnapshot
     * @return
     */
    DataRet addGoodSnapShoot(GoodSnapshot goodSnapshot);
}
