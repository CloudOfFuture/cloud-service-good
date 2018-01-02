package com.kunlun.api.mapper;

import com.github.pagehelper.Page;
import com.kunlun.entity.Estimate;
import com.kunlun.entity.Good;
import com.kunlun.entity.GoodExt;
import com.kunlun.entity.GoodSnapshot;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author JackSpeed
 * @version V1.0 <>
 * @date 17-12-22下午3:31
 * @desc
 */
@Mapper
public interface WxGoodMapper {

    /**
     * 查询商品详情
     *
     * @param goodId Long
     * @return GoodExt
     */
    GoodExt findById(@Param("goodId") Long goodId);

    /**
     * 访问量
     *
     * @param goodId Long
     * @return Integer
     */
    Integer updateVisitTotal(@Param("goodId") Long goodId);

    /**
     * 库存扣减
     *
     * @param goodId Long
     * @param stock  int
     * @return int
     */
    Integer updateGoodStockById(@Param("goodId") Long goodId,
                                @Param("stock") int stock);

    /**
     * 修改销量
     *
     * @param saleVolume int
     * @param goodId     Long
     * @return int
     */
    Integer updateSaleVolume(@Param("saleVolume") int saleVolume,
                             @Param("goodId") Long goodId);

    /**
     * 商品搜索
     *
     * @param searchKey  Long
     * @param categoryId String
     * @return List
     */
    Page<Good> findByCondition(@Param("categoryId") Long categoryId,
                               @Param("searchKey") String searchKey);

    /**
     * 获取商品快照详情
     *
     * @param orderId Long
     * @return GoodSnapshot
     */
    GoodSnapshot findByGoodSnapshot(@Param("orderId") Long orderId);

    /**
     * 活动(拼团)商品库存扣减
     *
     * @param id    Long
     * @param count int
     * @return Integer
     */
    Integer updateActivityGoodStockById(@Param("id") Long id,
                                        @Param("count") int count);

    /**
     * 查询商品评价列表
     *
     * @param goodId Long
     * @return List
     */
    Page<Estimate> findGoodEstimates(@Param("goodId") Long goodId);
}
