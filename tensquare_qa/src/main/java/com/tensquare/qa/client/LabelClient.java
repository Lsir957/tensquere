package com.tensquare.qa.client;

import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Feign接口
 *    1）类上面提供@FeignClient注解，写上需要调用的微服务
 *    2）从服务提供把需要调用的方法拷贝到接口里面来
 *    3）@RequestMapping的value属性值是否需要修改？？？ @PathVariable注解必须写上value值！！！
 */
@FeignClient("tensquare-base")
public interface LabelClient {

    @RequestMapping(value = "/label/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id") String id);

}
