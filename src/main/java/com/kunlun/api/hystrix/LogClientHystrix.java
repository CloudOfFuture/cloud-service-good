package com.kunlun.api.hystrix;

import com.kunlun.api.client.LogClient;
import com.kunlun.entity.GoodLog;
import com.kunlun.result.DataRet;
import org.springframework.stereotype.Component;

/**
 * @author by hmy
 * @version <0.1>
 * @created on 2017-12-27.
 */
@Component
public class LogClientHystrix implements LogClient{

    /**
     * 创建商品日志错误返回
     *
     * @param goodLog
     * @return
     */
    @Override
    public DataRet<String> saveGoodLog(GoodLog goodLog) {
        return new DataRet<>("ERROR","创建商品日志失败");
    }
}
