package com.kunlun.api.mapper;

import com.kunlun.entity.GoodSnapshot;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author by hws
 * @created on 2017/12/26.
 */
@Mapper
public interface WxGoodSnapShotMapper {


    /**
     * 新增商品快照
     * @param goodSnapshot
     * @return
     */
    Integer add(GoodSnapshot goodSnapshot);
}
