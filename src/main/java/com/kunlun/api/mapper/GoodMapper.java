package com.kunlun.api.mapper;

import com.github.pagehelper.Page;
import com.kunlun.entity.Good;
import com.kunlun.entity.GoodExt;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

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
    GoodExt findById(@Param("id") Long id);


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
    Page<GoodExt> list(@Param("sellerId") Long sellerId,
                                  @Param("type") String type,
                                  @Param("searchKey") String searchKey,
                                  @Param("goodNo") String goodNo,
                                  @Param("startDate") Date startDate,
                                  @Param("endDate") Date endDate,
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
    Page<GoodExt> findForActivity(@Param("sellerId") Long sellerId,
                                  @Param("type") String type,
                                  @Param("searchKey") String searchKey,
                                  @Param("goodNo") String goodNo,
                                  @Param("startDate") Date startDate,
                                  @Param("endDate") Date endDate,
                                  @Param("brandId") Long brandId,
                                  @Param("onSale") String onSale,
                                  @Param("categoryId") Long categoryId,
                                  @Param("hot") String hot,
                                  @Param("isNew") String isNew,
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
    Page<GoodExt> findByActivityId(@Param("sellerId") Long sellerId,
                                   @Param("type") String type,
                                   @Param("searchKey") String searchKey,
                                   @Param("goodNo") String goodNo,
                                   @Param("startDate") Date startDate,
                                   @Param("endDate") Date endDate,
                                   @Param("brandId") Long brandId,
                                   @Param("onSale") String onSale,
                                   @Param("categoryId") Long categoryId,
                                   @Param("hot") String hot,
                                   @Param("isNew") String isNew,
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
    Page<GoodExt> findForCategory(@Param("sellerId") Long sellerId,
                                  @Param("type") String type,
                                  @Param("searchKey") String searchKey,
                                  @Param("goodNo") String goodNo,
                                  @Param("startDate") Date startDate,
                                  @Param("endDate") Date endDate,
                                  @Param("brandId") Long brandId,
                                  @Param("onSale") String onSale,
                                  @Param("categoryId") Long categoryId,
                                  @Param("hot") String hot,
                                  @Param("isNew") String isNew,
                                  @Param("freight") String freight);


    /**
     * 根据id删除商品
     *
     * @param id
     * @return
     */
    Integer deleteById(@Param("id") Long id);


    /**
     * 批量删除
     *
     * @param idList
     * @return
     */
    Integer deleteByIdList(@Param("idList") List<Long> idList);


    /**
     * 修改商品信息
     *
     * @param good
     * @return
     */
    Integer update(Good good);


    /**
     * 商品上下架
     *
     * @param onSale
     * @param id
     * @return
     */
    Integer updateSaleStatus(@Param("onSale") String onSale,@Param("id") Long id);

    /**
     * 批量上下架商品
     *
     * @param onSale
     * @param goodIdList
     * @return
     */
    Integer updateSaleList(@Param("onSale") String onSale,@Param("goodIdList") List<Long> goodIdList);


    /**
     * 新建商品审核
     *
     * @param audit
     * @param reason
     * @param id
     * @return
     */
    Integer audit(@Param("audit") String audit,@Param("reason") String reason,@Param("id") Long id);


    /**
     * 更新库存
     *
     * @param id
     * @param count
     * @return
     */
    Integer updateStock(@Param("id") Long id,@Param("count") Integer count);

}
