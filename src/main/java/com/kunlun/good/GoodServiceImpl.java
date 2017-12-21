package com.kunlun.good;

import com.kunlun.entity.Good;
import com.kunlun.result.DataRet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author by hmy
 * @version <0.1>
 * @created on 2017-12-21.
 */
@Service
public class GoodServiceImpl implements GoodService{

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
        if (good==null){
            return new DataRet<>("ERROR","添加失败");
        }
        Integer result=goodMapper.add(good);
        if (result<=0){
            return new DataRet<>("ERROR","添加失败");
        }
        return new DataRet<>("添加成功");
    }
}
