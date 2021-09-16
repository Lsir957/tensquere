package com.tensquare.spit.controller;

import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/spit")
public class SpitController {

    @Autowired
    private SpitService spitService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查询全部
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        return new Result(true, StatusCode.OK,"查询成功",spitService.findAll());
    }

    /**
     * 根据id查询
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable String id){
        return new Result(true, StatusCode.OK,"查询成功",spitService.findById(id));
    }

    /**
     * 添加
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Spit spit){
        spitService.add(spit);
        return new Result(true, StatusCode.OK,"添加成功");
    }

    /**
     * 根据id删除
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String id){
        spitService.deleteById(id);
        return new Result(true, StatusCode.OK,"删除成功");
    }


    /**
     * 根据id修改
     * @param spit
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public Result update(@RequestBody Spit spit, @PathVariable String id ){
        spit.setId(id);
        spitService.update(spit);
        return new Result(true,StatusCode.OK,"修改成功");
    }


    @RequestMapping("/comment/{parentid}/{page}/{size}")
    public Result findParentid( @PathVariable Integer parentid,@PathVariable Integer page,@PathVariable Integer size){
        Page<Spit>  spitPage = spitService.findByParentid(parentid,page,size);
        return new Result(true,StatusCode.OK,"查询成功", new PageResult<Spit>(spitPage.getTotalElements(),spitPage.getContent()));
    }

    /**
     * 点赞
     */
    @RequestMapping(value = "/thumbup/{id}",method = RequestMethod.PUT)
    public Result thumbup(@PathVariable String id){
        String userId = "220";
        String flag = (String ) redisTemplate.opsForValue().get(userId+"_"+id);

        if ("1".equals(flag)){
            spitService.cancelThumbup(id);

            redisTemplate.delete(userId+"_"+id);

            return new Result(true,StatusCode.REPEAT_ERROR,"取消点赞");
        }
        spitService.updateThumbup(id);
        redisTemplate.opsForValue().set(userId+"_"+id,"1");
        return new Result(true,StatusCode.OK,"点赞成功");
    }

}
