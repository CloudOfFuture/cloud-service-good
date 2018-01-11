package com.kunlun.api.service;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kunlun.api.client.CategoryClient;
import com.kunlun.api.client.FileClient;
import com.kunlun.api.client.LogClient;
import com.kunlun.entity.GoodExt;
import com.kunlun.entity.GoodLog;
import com.kunlun.entity.MallImg;
import com.kunlun.enums.CommonEnum;
import com.kunlun.api.mapper.SellerGoodMapper;
import com.kunlun.result.DataRet;
import com.kunlun.result.PageResult;
import com.kunlun.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author JackSpeed
 * @version V1.0 <>
 * @date 17-12-21下午1:09
 * @desc
 */
@Service
public class SellerGoodServiceImpl implements SellerGoodService {


    @Autowired
    private SellerGoodMapper sellerGoodMapper;


    @Autowired
    private LogClient logClient;

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private FileClient fileClient;

    /**
     * 新增商品
     *
     * @param goodExt goodExt
     * @return DataRet
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public DataRet add(GoodExt goodExt) {
        int result = sellerGoodMapper.add(goodExt);
        if (result == 0) {
            return new DataRet<>("ERROR", "新增商品失败");
        }
        if (goodExt.getCategoryId() != null) {
            bindCategoryGood(goodExt.getCategoryId(), goodExt.getId());
        }
        List<MallImg> imgList = goodExt.getImgList();
        if (imgList != null && imgList.size() > 0) {
            for (MallImg mallImg : imgList) {
                mallImg.setType("TYPE_IMG_GOOD");
                mallImg.setTargetId(goodExt.getId());
                fileClient.add(mallImg);
            }
        }
        saveGoodLog(goodExt.getId(), goodExt.getGoodName(), "新增商品");
        return new DataRet<>("新增成功");
    }

    /**
     * 查询详情
     *
     * @param goodId Long
     * @return GoodExt
     */
    @Override
    public DataRet findById(Long goodId) {
        if (goodId == null) {
            return new DataRet<>("ERROR", "参数不能为空");
        }
        GoodExt good = sellerGoodMapper.findById(goodId);
        if (good == null) {
            return new DataRet<>("ERROR", "商品不存在");
        }
        //获取图片列表
        DataRet imgList = fileClient.list("TYPE_IMG_GOOD", goodId);
        //判断图片是否为空
        if (imgList.getBody() != null) {
            good.setImgList((List<MallImg>) imgList.getBody());
        }
        return new DataRet<>(good);
    }

    /**
     * 更新商品信息
     *
     * @param goodExt GoodExt
     * @return DataRet
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public DataRet updateGood(GoodExt goodExt) {
        if (goodExt.getId() == null) {
            return new DataRet<>("ERROR", "商品id不能为空");
        }

        GoodExt localGood = sellerGoodMapper.findById(goodExt.getId());
        if (localGood == null) {
            return new DataRet<>("ERROR", "未找到商品");
        }

        boolean updateFlag = CommonEnum.ON_SALE.getCode().equals(localGood.getOnSale());
        if (updateFlag) {
            //商品上架状态，不能修改
            return new DataRet<>("ERROR", "商品为上架状态，不能修改");
        }
        boolean unbindFlag = goodExt.getCategoryId() != null && !goodExt.getCategoryId().equals(localGood.getCategoryId());
        if (localGood.getCategoryId() != null && goodExt.getCategoryId() == null) {
            //类目解绑
            unbindCategoryGood(localGood.getId());
        } else if (unbindFlag) {
            //类目解绑
            unbindCategoryGood(localGood.getId());
            //绑定类目
            bindCategoryGood(goodExt.getCategoryId(), goodExt.getId());
        }

        sellerGoodMapper.updateGood(goodExt);
        // 图片删除更新
        List<MallImg> imgList = goodExt.getImgList();
        if (imgList != null && imgList.size() > 0) {
            for (MallImg mallImg : imgList) {
                fileClient.deleteById(mallImg.getId());
            }
            for (MallImg mallImg : imgList) {
                mallImg.setType("TYPE_IMG_GOOD");
                mallImg.setTargetId(goodExt.getId());
                fileClient.add(mallImg);
            }
        }
        saveGoodLog(goodExt.getGoodId(), goodExt.getGoodName(), "修改商品信息");
        return new DataRet<>("修改成功");
    }


    /**
     * 批量删除商品
     *
     * @param idList 商品id集合
     * @return DataRet
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public DataRet deleteByIdList(List<Long> idList) {
        if (idList == null || idList.size() == 0) {
            return new DataRet<>("ERROR", "参数错误");
        }
        int result = sellerGoodMapper.deleteByIdList(idList);
        if (result == 0) {
            return new DataRet<>("ERROR", "删除失败(请检查商品是否已经下架)");
        }
        idList.forEach(goodId -> saveGoodLog(goodId, "", "批量删除商品"));
        return new DataRet<>("删除成功");
    }

    /**
     * 修改库存
     *
     * @param id    商品id--主键
     * @param count 扣减、增加数量
     * @return DataRet
     */
    @Override
    public DataRet<String> updateGoodStock(Long id, Integer count) {
        int result = sellerGoodMapper.updateGoodStock(id, count);
        if (result == 0) {
            return new DataRet<>("ERROR", "修改失败");
        }
        GoodExt good = sellerGoodMapper.findById(id);
        saveGoodLog(good.getId(), good.getGoodName(), "修改商品库存");
        return new DataRet<>("修改成功");
    }


    /**
     * 批量商品上下架
     *
     * @param saleStatus 上下ON_SALE", "上架 OFF_SALE", "下架"),
     * @param goodIdList 商品id集合
     * @return DataRet
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public DataRet batchUpdateSaleStatus(String saleStatus, List<Long> goodIdList) {
        if (StringUtils.isEmpty(saleStatus) || goodIdList == null || goodIdList.size() == 0) {
            return new DataRet<>("ERROR", "参数错误");
        }
        sellerGoodMapper.batchUpdateSaleStatus(saleStatus, goodIdList);
        String action = CommonEnum.ON_SALE.getCode().equals(saleStatus) ? "商品批量上架" : "商品批量下架";
        goodIdList.forEach(id -> saveGoodLog(id, "", action));
        return new DataRet<>(action + "成功");
    }

    /**
     * 根据id删除商品
     *
     * @param id
     * @return
     */
    @Override
    public DataRet delete(Long id) {
        if (id==null){
            return new DataRet("ERROR","参数错误");
        }
        //获取商品详情
        GoodExt goodExt=sellerGoodMapper.findById(id);
        if (goodExt==null){
            return new DataRet("ERROR","查无结果");
        }
        Integer result=sellerGoodMapper.delete(id);
        if (result>0){
            saveGoodLog(id,goodExt.getGoodName(),"删除商品成功");
            return new DataRet("删除成功");
        }
        saveGoodLog(id,goodExt.getGoodName(),"删除商品失败");
        return new DataRet("ERROR","删除失败");
    }


    /**
     * 条件查询商品列表
     *
     * @param pageNo     Integer
     * @param pageSize   Integer
     * @param sellerId   Long
     * @param type       UNBIND_CATEGORY 未绑定类目
     *                   UNBIND_ACTIVITY 未绑定活动
     *                   BIND_ACTIVITY 已经绑定活动
     * @param searchKey  String
     * @param goodNo     String
     * @param startDate  String
     * @param endDate    String
     * @param brandId    Long
     * @param saleStatus String
     * @param categoryId Long
     * @param hot        String
     * @param isNew      String
     * @param freight    String
     * @return List
     */
    @Override
    public PageResult findByCondition(Integer pageNo,
                                      Integer pageSize,
                                      Long sellerId,
                                      String type,
                                      String searchKey,
                                      String goodNo,
                                      String startDate,
                                      String endDate,
                                      Long brandId,
                                      String saleStatus,
                                      Long categoryId,
                                      String hot,
                                      String isNew,
                                      String freight) {
        if (sellerId == null) {
            return new PageResult();
        }
        PageHelper.startPage(pageNo, pageSize);
        if (!StringUtils.isEmpty(searchKey)) {
            searchKey = "%" + searchKey + "%";
        }
        startDate = CommonUtil.formatDate(startDate);
        endDate = CommonUtil.formatDate(endDate);
        Page<GoodExt> page;
        boolean getUnbindCategory = CommonEnum.UNBIND_CATEGORY.getCode().equals(type);
        boolean getBindActivity = CommonEnum.BIND_ACTIVITY.getCode().equals(type);
        boolean getUnbindActivity = CommonEnum.UNBIND_ACTIVITY.getCode().equals(type);
        if (getUnbindCategory) {
            //未绑定类目的商品列表
            page = sellerGoodMapper.findForCategory(sellerId, type, searchKey, goodNo, startDate,
                    endDate, brandId, saleStatus, categoryId, hot, isNew, freight);
        } else if (getBindActivity) {
            //已经绑定活动的商品列表搜索
            page = sellerGoodMapper.findByActivityId(sellerId, type, searchKey, goodNo, startDate,
                    endDate, brandId, saleStatus, categoryId, hot, isNew, freight);
        } else if (getUnbindActivity) {
            //未绑定活动的商品列表
            page = sellerGoodMapper.findForActivity(sellerId, type, searchKey, goodNo, startDate,
                    endDate, brandId, saleStatus, categoryId, hot, isNew, freight);
        } else {
            //基础条件查询
            page = sellerGoodMapper.findByCondition(sellerId, type, searchKey, goodNo, startDate,
                    endDate, brandId, saleStatus, categoryId, hot, isNew, freight);
        }
        return new PageResult<>(page);
    }


    /**
     * 商品绑定类目
     *
     * @param categoryId Long
     * @param goodId     Long
     */
    private void bindCategoryGood(Long categoryId, Long goodId) {
        categoryClient.bind(categoryId, goodId);
    }

    /**
     * 商品解绑类目
     *
     * @param goodId Long
     */
    private void unbindCategoryGood(Long goodId) {
        if (goodId == null) {
            return;
        }
        categoryClient.unbinding(goodId);
    }

    /**
     * 保持商品操作日志
     *
     * @param goodName String
     * @param action   String
     * @param goodId   Long
     */
    private void saveGoodLog(Long goodId, String goodName, String action) {
        GoodLog goodLog = new GoodLog();
        goodLog.setGoodId(goodId);
        goodLog.setGoodName(goodName);
        goodLog.setAction(action);
        logClient.saveGoodLog(goodLog);
    }


}
