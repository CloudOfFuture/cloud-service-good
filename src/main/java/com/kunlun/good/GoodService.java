package com.kunlun.good;

import com.kunlun.entity.Good;
import com.kunlun.result.DataRet;

/**
 * @author by hmy
 * @version <0.1>
 * @created on 2017-12-21.
 */
public interface GoodService {

    /**
     * 创建商品
     *
     * @param good
     * @return
     */
    DataRet<String> add(Good good);
}
