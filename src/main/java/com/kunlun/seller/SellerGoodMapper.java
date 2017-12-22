package com.kunlun.seller;

import com.github.pagehelper.Page;
import com.kunlun.entity.Good;
import com.kunlun.entity.GoodExt;
import com.kunlun.entity.GoodRequestParams;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author JackSpeed
 * @version V1.0 <>
 * @date 17-12-21下午12:55
 * @desc
 */
@Mapper
public interface SellerGoodMapper {

    /**
     * 创建商品
     *
     * @param good Good
     * @return Integer
     */
    Integer add(Good good);

    /**
     * 根据id查询商品详情
     *
     * @param goodId Long
     * @return GoodExt
     */
    GoodExt findById(@Param("goodId") Long goodId);

    /**
     * 商品库存扣减
     *
     * @param goodId Long
     * @param count  正数为添加、负数为扣减
     * @return Integer
     */
    Integer updateGoodStock(@Param("goodId") Long goodId,
                            @Param("count") Integer count);

    /**
     * 更新商品信息
     *
     * @param good Good
     * @return Integer
     */
    Integer updateGood(Good good);


    /**
     * 商品上下架
     *
     * @param saleStatus saleStatus 1上架 0下架 默认上架
     * @param idList     商品id集合
     * @return Integer
     */
    Integer batchUpdateSaleStatus(@Param("saleStatus") String saleStatus,
                                  @Param("idList") List<Long> idList);

    /**
     * 根据商品id删除商品
     *
     * @param idList 商品id
     * @return Integer
     */
    Integer deleteByIdList(@Param("idList") List<Long> idList);


    /**
     * 条件查询
     *
     * @param userId     Long
     * @param type       String
     * @param searchKey  String
     * @param goodNo     String
     * @param startDate  String
     * @param endDate    String
     * @param brandId    String
     * @param saleStatus String
     * @param categoryId Long
     * @param hot        String
     * @param isNew      String
     * @param freight    String
     * @return List
     */
    Page<GoodExt> findByCondition(@Param("userId") Long userId,
                                  @Param("type") String type,
                                  @Param("searchKey") String searchKey,
                                  @Param("goodNo") String goodNo,
                                  @Param("startDate") String startDate,
                                  @Param("endDate") String endDate,
                                  @Param("brandId") Long brandId,
                                  @Param("saleStatus") String saleStatus,
                                  @Param("categoryId") Long categoryId,
                                  @Param("hot") String hot,
                                  @Param("isNew") String isNew,
                                  @Param("freight") String freight);


    /**
     * 未绑定活动的商品列表
     *
     * @param userId     Long
     * @param type       String
     * @param searchKey  String
     * @param goodNo     String
     * @param startDate  String
     * @param endDate    String
     * @param brandId    String
     * @param onSale     String
     * @param categoryId Long
     * @param hot        String
     * @param isNew      String
     * @param freight    String
     * @return Page
     */
    Page<GoodExt> findForActivity(@Param("user_id") Long userId,
                                  @Param("type") String type,
                                  @Param("searchKey") String searchKey,
                                  @Param("good_no") String goodNo,
                                  @Param("start_date") String startDate,
                                  @Param("end_date") String endDate,
                                  @Param("brand_id") Long brandId,
                                  @Param("on_sale") String onSale,
                                  @Param("category_id") Long categoryId,
                                  @Param("hot") String hot,
                                  @Param("is_new") String isNew,
                                  @Param("freight") String freight);

    /**
     * 根据活动id作为主要条件查询列表
     *
     * @param userId     Long
     * @param type       String
     * @param searchKey  String
     * @param goodNo     String
     * @param startDate  String
     * @param endDate    String
     * @param brandId    String
     * @param onSale     String
     * @param categoryId Long
     * @param hot        String
     * @param isNew      String
     * @param freight    String
     * @return Page
     */
    Page<GoodExt> findByActivityId(@Param("user_id") Long userId,
                                   @Param("type") String type,
                                   @Param("searchKey") String searchKey,
                                   @Param("good_no") String goodNo,
                                   @Param("start_date") String startDate,
                                   @Param("end_date") String endDate,
                                   @Param("brand_id") Long brandId,
                                   @Param("on_sale") String onSale,
                                   @Param("category_id") Long categoryId,
                                   @Param("hot") String hot,
                                   @Param("is_new") String isNew,
                                   @Param("freight") String freight);

    /**
     * 未绑定类目的商品列表s
     *
     * @param userId     Long
     * @param type       String
     * @param searchKey  String
     * @param goodNo     String
     * @param startDate  String
     * @param endDate    String
     * @param brandId    String
     * @param onSale     String
     * @param categoryId Long
     * @param hot        String
     * @param isNew      String
     * @param freight    String
     * @return Page
     */
    Page<GoodExt> findForCategory(@Param("user_id") Long userId,
                                  @Param("type") String type,
                                  @Param("searchKey") String searchKey,
                                  @Param("good_no") String goodNo,
                                  @Param("start_date") String startDate,
                                  @Param("end_date") String endDate,
                                  @Param("brand_id") Long brandId,
                                  @Param("on_sale") String onSale,
                                  @Param("category_id") Long categoryId,
                                  @Param("hot") String hot,
                                  @Param("is_new") String isNew,
                                  @Param("freight") String freight);
}
