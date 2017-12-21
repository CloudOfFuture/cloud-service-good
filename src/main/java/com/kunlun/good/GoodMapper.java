package com.kunlun.good;

import com.github.pagehelper.Page;
import com.kunlun.entity.Good;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * @author by hmy
 * @version <0.1>
 * @created on 2017-12-21.
 */
@Mapper
public interface GoodMapper {

    /**
     * 创建商品
     *
     * @param good
     * @return
     */
    Integer add(Good good);

    /**
     * 获取商品详情
     *
     * @param id
     * @return
     */
    Good findById(@Param("id") Long id);


    /**
     * 分页查询
     *
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
    Page<Good> list(@Param("searchKey") String searchKey,
                    @Param("goodNo") String goodNo,
                    @Param("startDate")Date startDate,
                    @Param("endDate") Date endDate,
                    @Param("brandId") Long brandId,
                    @Param("onSale") String onSale,
                    @Param("categoryId") Long categoryId,
                    @Param("hot") String hot,
                    @Param("isNew") String isNew,
                    @Param("freight") String freight);
}
