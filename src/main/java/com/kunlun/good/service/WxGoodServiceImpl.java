package com.kunlun.good.service;


import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kunlun.entity.*;
import com.kunlun.enums.CommonEnum;
import com.kunlun.good.mapper.WxGoodMapper;
import com.kunlun.result.DataRet;
import com.kunlun.result.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author JackSpeed
 * @version V1.0 <>
 * @date 17-12-22下午3:32
 * @desc 微信小程序商品模块
 */
@Service
public class WxGoodServiceImpl implements WxGoodService {

    @Autowired
    WxGoodMapper wxGoodMapper;


    /**
     * 查询商品详情
     *
     * @param goodId Long
     * @return GoodExt
     */
    @Override
    public DataRet findById(Long goodId) {
        if (goodId == null) {
            return new DataRet<>("query_error", "参数错误");
        }
        GoodExt good = wxGoodMapper.findById(goodId);
        if (good == null) {
            return new DataRet<>("ERROR", "没有该商品");
        }
        if (CommonEnum.OFF_SALE.getCode().equals(good.getOnSale())) {
            return new DataRet<>("ERROR", "商品已经下架");
        }
//        //获取banner图片列表
//        List<MallImage> imgList = fileOperationMapper.findByTargetId(good.getId(), 0);
//        good.setImgList(imgList);
        //TODO:获取图片
        wxGoodMapper.updateVisitTotal(goodId);
        return new DataRet<>(good);
    }

    /**
     * 查询商品评价列表
     *
     * @param pageNo   Integer
     * @param pageSize Integer
     * @param goodId   Long
     * @return List
     */
    @Override
    public PageResult findEstimateList(Integer pageNo, Integer pageSize, Long goodId) {
        if (pageNo == null || pageSize == null || goodId == null) {
            return new PageResult<>();
        }
        PageHelper.startPage(pageNo, pageSize);
        Page<Estimate> page = wxGoodMapper.findGoodEstimates(goodId);
        return new PageResult<>(page);
    }

    /**
     * 条件查询
     *
     * @param pageNo     Integer
     * @param pageSize   Integer
     * @param categoryId Long
     * @param searchKey  String
     * @return List
     */
    @Override
    public PageResult findByCondition(Integer pageNo, Integer pageSize,
                                      Long categoryId, String searchKey) {
        if (pageNo == null || pageSize == null) {
            return new PageResult();
        }
        PageHelper.startPage(pageNo, pageSize);
        if (!StringUtils.isEmpty(searchKey)) {
            searchKey = "%" + searchKey + "%";
        }
        Page<Good> page = wxGoodMapper.findByCondition(categoryId, searchKey);
        return new PageResult<>(page);
    }


    /**
     * 根据订单id查询商品快照
     *
     * @param orderId Long
     * @return GoodSnapshot
     */
    @Override
    public DataRet findGoodSnapshot(Long orderId) {
        if (orderId == null) {
            return new DataRet("param_error", "参数错误");
        }
        GoodSnapshot goodSnapshot = wxGoodMapper.findByGoodSnapshot(orderId);
        return new DataRet<>(goodSnapshot);
    }
}
