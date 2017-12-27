package com.kunlun.api.client;

import com.kunlun.api.hystrix.LogClientHystrix;
import com.kunlun.entity.GoodLog;
import com.kunlun.result.DataRet;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author by hmy
 * @version <0.1>
 * @created on 2017-12-27.
 */
@FeignClient(value = "cloud-service-log",fallback = LogClientHystrix.class)
public interface LogClient {

    /**
     * 创建商品日志
     *
     * @param goodLog
     * @return
     */
    @PostMapping("/log/add/goodLog")
     DataRet<String> saveGoodLog(@RequestBody GoodLog goodLog);
}
