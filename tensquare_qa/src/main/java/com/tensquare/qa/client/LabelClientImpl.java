package com.tensquare.qa.client;

import entity.Result;
import entity.StatusCode;
import org.springframework.stereotype.Component;

/**
 * 熔断实现类
 */
@Component
public class LabelClientImpl implements LabelClient{

    @Override
    public Result findById(String id) {
        return new Result(false, StatusCode.ERROR,"服务暂时不可以，切换到熔断器啦..");
    }
}
