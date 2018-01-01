package com.kunlun.api.hystrix;

import com.kunlun.api.client.CategoryClient;
import com.kunlun.result.DataRet;
import org.springframework.stereotype.Component;

/**
 * @author by hmy
 * @version <0.1>
 * @created on 2017-12-27.
 */
@Component
public class CategoryClientHystrix implements CategoryClient {

    /**
     * 绑定失败回调
     *
     * @param categoryId
     * @param goodId
     * @return
     */
    @Override
    public DataRet<String> bind(Long categoryId, Long goodId) {
        return new DataRet<>("ERROR","绑定失败");
    }

    /**
     *
     *
     * @param goodId
     * @return
     */
    @Override
    public DataRet<String> unbinding(Long goodId) {
        return new DataRet<>("ERROR","解绑失败");
    }
}
