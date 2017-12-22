package com.kunlun.seller;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kunlun.category.CategoryGoodMapper;
import com.kunlun.entity.GoodExt;
import com.kunlun.entity.GoodRequestParams;
import com.kunlun.entity.MallImage;
import com.kunlun.enums.CommonEnum;
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
    private CategoryGoodMapper categoryGoodMapper;

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
//        //更新图片信息
//        List<MallImage> imgList = goodExt.getImgList();
//        if (imgList != null && imgList.size() > 0) {
//            for (MallImage mallImage : imgList) {
//                mallImage.setGoodId(goodExt.getId());
//                fileOperationMapper.add(mallImage);
        //TODO：图片保存
//            }
//        }
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
//        List<MallImage> imgList = fileOperationMapper.findByTargetId(good.getId(), 0);
//        good.setImgList(imgList);
        //TODO：图片查询
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
        List<MallImage> imgList = goodExt.getImgList();
//        if (imgList != null && imgList.size() > 0) {
//            //清空原来的图片
//            imgList.forEach(item -> fileOperationMapper.deleteById(item.getId()));
//            //加入新图片
//            imgList.forEach(item -> {
//                item.setGoodId(goodExt.getId());
//                fileOperationMapper.add(item);
//            });
        //TODO：图片操作
//        }
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
    public DataRet batchDeleteGood(List<Long> idList) {
        if (idList == null || idList.size() == 0) {
            return new DataRet<>("ERROR", "参数错误");
        }
        int result = sellerGoodMapper.batchDeleteGood(idList);
        if (result == 0) {
            return new DataRet<>("ERROR", "删除失败");
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
    public DataRet updateGoodStock(Long id, Integer count) {
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
     * 条件查询列表（未绑定活动，未绑定类目，已经绑定活动，模糊查询）
     *
     * @param params GoodRequestParams
     * @return PageResult
     */
    @Override
    public PageResult findByCondition(GoodRequestParams params) {
        if (params.getSellerId() == null) {
            return new PageResult();
        }
        PageHelper.startPage(params.getPageNo(), params.getPageSize());
        if (StringUtils.isEmpty(params.getSearchKey())) {
            params.setSearchKey(null);
        }
        if (!StringUtils.isEmpty(params.getSearchKey())) {
            params.setSearchKey("%" + params.getSearchKey() + "%");
        }
        params.setEndDate(CommonUtil.formatDate(params.getEndDate()));
        params.setStartDate(CommonUtil.formatDate(params.getStartDate()));
        Page<GoodExt> page;
        boolean getUnbindCategory = CommonEnum.UNBIND_CATEGORY.getCode().equals(params.getType());
        boolean getBindActivity = CommonEnum.BIND_ACTIVITY.getCode().equals(params.getType());
        boolean getUnbindActivity = CommonEnum.UNBIND_ACTIVITY.getCode().equals(params.getType());
        if (getUnbindCategory) {
            //未绑定类目的商品列表
            page = sellerGoodMapper.findForCategory(params);
        } else if (getBindActivity) {
            //已经绑定活动的商品列表搜索
            page = sellerGoodMapper.findByActivityId(params);
        } else if (getUnbindActivity) {
            //未绑定活动的商品列表
            page = sellerGoodMapper.findForActivity(params);
        } else {
            //基础条件查询
            page = sellerGoodMapper.findByCondition(params);
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
        categoryGoodMapper.bindCategoryGood(categoryId, goodId);
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
        categoryGoodMapper.unbindWithGoodId(goodId);
    }

    /**
     * 保持商品操作日志
     *
     * @param goodName String
     * @param action   String
     * @param goodId   Long
     */
    private void saveGoodLog(Long goodId, String goodName, String action) {
        //TODO:保存日志
    }


}
