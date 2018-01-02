package com.kunlun.api.hystrix;

import com.kunlun.api.client.FileClient;
import com.kunlun.entity.MallImg;
import com.kunlun.result.DataRet;
import org.springframework.stereotype.Component;

/**
 * @author by hmy
 * @version <0.1>
 * @created on 2017-12-29.
 */
@Component
public class FileClientHystrix implements FileClient{


    /**
     * 添加图片
     *
     * @param mallImg 图片对象
     * @return
     */
    @Override
    public DataRet<String> add(MallImg mallImg) {
        return new DataRet<>("ERROR","添加图片失败");
    }


    /**
     * 获取图片列表
     *
     * @param type
     * @param targetId
     * @return
     */
    @Override
    public DataRet list(String type, Long targetId) {
        return new DataRet<>("ERROR","获取图片列表失败");
    }


    /**
     * 删除图片
     *
     * @param id 图片id
     * @return
     */
    @Override
    public DataRet deleteById(Long id) {
        return new DataRet("ERROR","删除图片失败");
    }
}
