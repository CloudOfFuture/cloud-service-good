package com.kunlun.good.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import com.kunlun.entity.Good;
import com.kunlun.good.mapper.GoodMapper;
import com.kunlun.result.DataRet;
import com.kunlun.result.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    /**
     * 创建商品
     *
     * @param good
     * @return
     */
    @Override
    public DataRet<String> add(Good good) {
        if (good == null) {
            return new DataRet<>("ERROR", "添加失败");
        }
        Integer result = goodMapper.add(good);
        if (result <= 0) {
            return new DataRet<>("ERROR", "添加失败");
        }
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
                                      String hot, String isNew, String freight) {
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
        Page<Good> page = goodMapper.list(searchKey, goodNo, startDate, endDate, brandId, onSale, categoryId, hot, isNew, freight);
        return new PageResult(page);
    }


    /**
     * 根据id删除商品
     *
     * @param id
     * @return
     */
    @Override
    public DataRet<String> deleteById(Long id) {
        Integer result = goodMapper.deleteById(id);
        if (result <= 0) {
            return new DataRet<>("ERROR", "删除失败");
        }
        return new DataRet<>("删除成功");
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
            return new DataRet<>("ERROR", "批量删除失败");
        }
        if (idList.size() > result) {
            return new DataRet<>("ERROR", "未完全删除,部分商品已删除");
        }
        //TODO 记录商品日志
        return new DataRet<>("批量删除成功");
    }
}
