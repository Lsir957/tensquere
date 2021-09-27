package com.tensquare.friend.client;

import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 用户微服务Feign接口
 */
@FeignClient(value = "tensquare-user")
public interface UserClient {

    /**
     * 更新关注数
     */
    @RequestMapping(value = "/user/updateFollowcount/{userid}/{count}" ,method = RequestMethod.PUT)
    public Result updateFollowcount(@PathVariable(value = "userid") String userid, @PathVariable(value = "count") Integer count);

    /**
     * 更新粉丝数
     */
    @RequestMapping(value = "/user/updateFanscount/{userid}/{count}" ,method = RequestMethod.PUT)
    public Result updateFanscount(@PathVariable("userid") String userid,@PathVariable(value = "count") Integer count);

}
