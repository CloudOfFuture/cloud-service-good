package com.kunlun.good;

import com.kunlun.entity.Good;
import com.kunlun.result.DataRet;
import com.kunlun.result.PageResult;

import java.util.Date;

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
    DataRet<String> add(Good good);

    /**
     * 获取详情
     *
     * @param id
     * @return
     */
    DataRet<Good> findById(Long id);


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
                               String hot, String isNew, String freight);
}
