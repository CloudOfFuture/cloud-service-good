package com.kunlun.api.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import com.kunlun.api.client.CategoryClient;
import com.kunlun.api.client.FileClient;
import com.kunlun.api.client.LogClient;
import com.kunlun.entity.*;
import com.kunlun.enums.CommonEnum;
import com.kunlun.api.mapper.GoodMapper;
import com.kunlun.result.DataRet;
import com.kunlun.result.PageResult;
import com.kunlun.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

/**
 * @author by hmy
 * @version <0.1>
 * @created on 2017-12-21.
 */
@Service
public class GoodServiceImpl implements GoodService {

    @Autowired
    private GoodMapper goodMapper;

    @Autowired
    private LogClient logClient;

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private FileClient fileClient;


    /**
     * 创建商品
     *
     * @param good
     * @return
     */
    @Override
    public DataRet<String> add(GoodExt good) {
        if (good == null) {
            return new DataRet<>("ERROR", "添加失败");
        }
        Integer result = goodMapper.add(good);
        if (result == 0) {
            return new DataRet<>("ERROR", "添加失败");
        }
        List<MallImg> imgList = good.getImgList();
        if (imgList != null && imgList.size() > 0) {
            for (MallImg mallImg : imgList) {
                mallImg.setTargetId(good.getId());
                mallImg.setType("TYPE_IMG_GOOD");
                fileClient.add(mallImg);
            }
        }
        if (good.getCategoryId() != null) {
            categoryClient.bind(good.getCategoryId(), good.getId());
        }
        addGoodLog(good.getGoodName(), "添加商品", good.getId());
        return new DataRet<>("添加成功");
    }


    /**
     * 获取商品详情
     *
     * @param id
     * @return
     */
    @Override
    public DataRet<Good> findById(Long id) {
        if (id == null) {
            return new DataRet<>("ERROR", "id为空");
        }
        Good good = goodMapper.findById(id);
        if (good == null) {
            return new DataRet<>("ERROR", "未找到");
        }
        //TODO 获取图片列表
        return new DataRet<>(good);
    }

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
    @Override
    public PageResult findByCondition(Integer pageNo, Integer pageSize, String searchKey, String goodNo,
                                      Date startDate, Date endDate, Long brandId, String onSale, Long categoryId,
                                      String hot, String isNew, String freight, Long sellerId, String type) {
        if (sellerId == null) {
            return new PageResult();
        }
        if (StringUtil.isEmpty(String.valueOf(pageNo)) || StringUtil.isEmpty(String.valueOf(pageSize))) {
            return new PageResult("ERROR", "参数错误");
        }
        PageHelper.startPage(pageNo, pageSize);
        if (StringUtil.isEmpty(searchKey)) {
            searchKey = null;
        }
        if (!StringUtil.isEmpty(searchKey)) {
            searchKey = ("%" + searchKey + "%");
        }
        Page<GoodExt> page;
        boolean getUnbindCategory = CommonEnum.UNBIND_CATEGORY.getCode().equals(type);
        boolean getBindActivity = CommonEnum.BIND_ACTIVITY.getCode().equals(type);
        boolean getUnbindActivity = CommonEnum.UNBIND_ACTIVITY.getCode().equals(type);
        if (getUnbindCategory) {
            //未绑定类目的商品列表
            page = goodMapper.findForCategory(sellerId, type, searchKey, goodNo, startDate,
                    endDate, brandId, onSale, categoryId, hot, isNew, freight);
        } else if (getBindActivity) {
            //已经绑定活动的商品列表搜索
            page = goodMapper.findByActivityId(sellerId, type, searchKey, goodNo, startDate,
                    endDate, brandId, onSale, categoryId, hot, isNew, freight);
        } else if (getUnbindActivity) {
            //未绑定活动的商品列表
            page = goodMapper.findForActivity(sellerId, type, searchKey, goodNo, startDate,
                    endDate, brandId, onSale, categoryId, hot, isNew, freight);
        } else {
            //基础条件查询
            page = goodMapper.list(sellerId, type, searchKey, goodNo, startDate,
                    endDate, brandId, onSale, categoryId, hot, isNew, freight);
        }
        return new PageResult<>(page);
    }


    /**
     * 根据id删除商品
     *
     * @param id
     * @return
     */
    @Override
    public DataRet<String> deleteById(Long id) {
        Good good = goodMapper.findById(id);
        Integer result = goodMapper.deleteById(id);
        if (result > 0) {
            addGoodLog(good.getGoodName(), "删除商品成功", id);
            return new DataRet<>("删除商品成功");
        }
        addGoodLog(good.getGoodName(), "删除商品失败", id);
        return new DataRet<>("ERROR", "删除商品失败");
    }


    /**
     * 批量删除商品
     *
     * @param idList
     * @return
     */
    @Override
    public DataRet<String> deleteByIdList(List<Long> idList) {
        if (idList == null || idList.size() == 0) {
            return new DataRet<>("ERROR", "参数错误");
        }
        Integer result = goodMapper.deleteByIdList(idList);
        if (result == 0) {
            idList.forEach(goodId -> {
                addGoodLog("", "批量删除失败", goodId);
            });
            return new DataRet<>("ERROR", "批量删除失败");
        }
        if (idList.size() > result) {
            return new DataRet<>("ERROR", "未完全删除,部分商品已删除");
        }
        idList.forEach(goodId -> {
            addGoodLog("", "批量删除商品成功", goodId);
        });
        return new DataRet<>("批量删除成功");
    }

    /**
     * 修改商品
     *
     * @param good
     * @return
     */
    @Override
    public DataRet<String> update(Good good) {
        if (good.getId() == null) {
            return new DataRet<>("ERROR", "参数错误");
        }
        Good newGood = goodMapper.findById(good.getId());
        if (newGood == null) {
            return new DataRet<>("ERROR", "未找到商品");
        }
        if (CommonEnum.ON_SALE.getCode().equals(newGood.getOnSale())) {
            return new DataRet<>("ERROR", "上架商品不能修改");
        }
        if (newGood.getCategoryId() != null && good.getCategoryId() == null) {
            categoryClient.unbinding(newGood.getId());
        }
        if (newGood.getCategoryId() != good.getCategoryId()) {
            categoryClient.unbinding(newGood.getId());
            categoryClient.bind(good.getCategoryId(), good.getId());
        }
        //TODO 图片删除更新
        Integer result = goodMapper.update(good);
        if (result == 0) {
            return new DataRet<>("ERROR", "修改失败");
        }
        return new DataRet<>("修改成功");
    }


    /**
     * 商品上下架
     *
     * @param onSale
     * @param id
     * @return
     */
    @Override
    public DataRet<String> updateSaleStatus(String onSale, Long id) {
        Good good = goodMapper.findById(id);
        if (StringUtil.isEmpty(onSale) || id == null) {
            return new DataRet<>("ERROR", "参数错误");
        }
        Integer result = goodMapper.updateSaleStatus(onSale, id);
        if (result > 0) {
            String saleResult = CommonEnum.ON_SALE.getCode().equals(onSale) ? "商品上架成功" : "商品下架成功";
            addGoodLog(good.getGoodName(), saleResult, id);
            return new DataRet<>(saleResult);
        }
        addGoodLog(good.getGoodName(), CommonEnum.ON_SALE.getCode().equals(onSale) ? "商品上架失败" : "商品下架失败", id);
        return new DataRet<>("ERROR", CommonEnum.ON_SALE.getCode().equals(onSale) ? "商品上架失败" : "商品下架失败");
    }


    /**
     * 商品批量上下架
     *
     * @param onSale
     * @param goodIdList
     * @return
     */
    @Override
    public DataRet<String> updateSaleList(String onSale, List<Long> goodIdList) {
        if (StringUtil.isEmpty(onSale) || goodIdList == null || goodIdList.size() == 0) {
            return new DataRet<>("ERROR", "参数错误");
        }
        Integer result = goodMapper.updateSaleList(onSale, goodIdList);
        if (result > 0 && result == goodIdList.size()) {
            String updateResult = CommonEnum.ON_SALE.getCode().equals(onSale) ? "商品批量上架成功" : "商品批量下架成功";
            goodIdList.forEach(goodId -> addGoodLog("", updateResult, goodId));
            return new DataRet<>(updateResult);
        }
        if (goodIdList.size() > result) {
            return new DataRet<>("ERROR", "部分商品上下架成功");
        }
        return new DataRet<>("ERROR", CommonEnum.ON_SALE.getCode().equals(onSale) ? "商品批量上架失败" : "商品批量下架失败");
    }


    /**
     * 新建商品审核
     *
     * @param audit
     * @param reason
     * @param id
     * @return
     */
    @Override
    public DataRet<String> audit(String audit, String reason, Long id) {
        if (id == null) {
            return new DataRet<>("ERROR", "参数错误");
        }
        if (audit.equals(CommonEnum.NOT_PASS_AUTH.getCode()) && StringUtil.isEmpty(reason)) {
            return new DataRet<>("ERROR", "请填写失败原因");
        }
        Integer result = goodMapper.audit(audit, reason, id);
        if (result == 0) {
            return new DataRet<>("ERROR", "审核失败");
        }
        return new DataRet<>("审核成功");
    }


    /**
     * 商品库存
     *
     * @param id
     * @param count
     * @return
     */
    @Override
    public DataRet<String> updateStock(Long id, Integer count) {
        Good good = goodMapper.findById(id);
        Integer result = goodMapper.updateStock(id, count);
        if (result == 0) {
            addGoodLog(good.getGoodName(), "库存修改失败", id);
            return new DataRet<>("ERROR", "库存修改失败");
        }
        addGoodLog(good.getGoodName(), "库存修改成功", id);
        return new DataRet<>("库存修改成功");
    }

    /**
     * 商品检查
     *
     * @param goodId
     * @param count
     * @param orderFee
     * @return
     */
    @Override
    public DataRet<Good> checkGood(Long goodId, Integer count, Integer orderFee) {
        Good good = goodMapper.findById(goodId);
        if (null == good || good.getStock() <= 0) {
            return new DataRet<>("ERROR", "商品库存不足");
        }
        if (CommonEnum.OFF_SALE.getCode().equals(good.getOnSale())) {
            return new DataRet<>("ERROR", "商品已下架");
        }
        if (orderFee != 0 && count != 0) {
            int unitFee = orderFee / count;
            if (good.getPrice() != unitFee) {
                return new DataRet<>("ERROR", "商品信息已过期，请重新下单");
            }
        }
        return new DataRet<>(good);
    }

    /**
     * 修改商品库存
     *
     * @param goodList List
     * @return
     */
    @Override
    public DataRet<String> updateStocks(List<Good> goodList) {
        goodList.forEach(item -> {
            Integer result = goodMapper.updateStock(item.getId(), item.getStock());
            if (result == 0) {
                addGoodLog(item.getGoodName(), "库存修改失败", item.getId());
            }
            addGoodLog(item.getGoodName(), "库存修改成功", item.getId());
        });
        return new DataRet<>("ERROR", "库存修改成功");
    }

    /**
     * 添加商品日志
     *
     * @param goodName
     * @param action
     * @param goodId
     */
    private void addGoodLog(String goodName, String action, Long goodId) {
        GoodLog goodLog = new GoodLog();
        goodLog.setGoodName(goodName);
        goodLog.setAction(action);
        goodLog.setGoodId(goodId);
        logClient.saveGoodLog(goodLog);
    }
}
