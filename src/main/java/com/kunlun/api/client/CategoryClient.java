package com.kunlun.api.client;

import com.kunlun.api.hystrix.CategoryClientHystrix;
import com.kunlun.result.DataRet;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author by hmy
 * @version <0.1>
 * @created on 2017-12-27.
 */
@FeignClient(value = "cloud-service-common",fallback = CategoryClientHystrix.class)
public interface CategoryClient {

    /**
     * 商品绑定类目
     *
     * @param categoryId
     * @param goodId
     * @return
     */
    @PostMapping("/category/bindCategoryGood")
    DataRet<String> bind(@RequestParam(value = "categoryId") Long categoryId,
                                @RequestParam(value = "goodId") Long goodId);


    /**
     * 商品解绑类目
     *
     * @param goodId
     * @return
     */
    @PostMapping("/category/unbindCategoryGood")
     DataRet<String> unbinding(@RequestParam(value = "goodId") Long goodId);
}
