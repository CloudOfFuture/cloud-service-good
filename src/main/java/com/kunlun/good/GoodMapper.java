package com.kunlun.good;

import com.kunlun.entity.Good;
import org.apache.ibatis.annotations.Mapper;

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
}
