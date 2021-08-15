package com.tensquare.base.controller;

import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/label")
public class LabelController {
    @Autowired
    private LabelService labelService;

    //条件查询
    public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<Label> pageList = labelService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Label>(pageList.getTotalElements(), pageList.getContent()));
    }

    //查询所有
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", labelService.findAll());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable("id") String id) {
        return new Result(true, StatusCode.OK, "查询成功", labelService.findById(id));
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result add(Label label) {
        labelService.add(label);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Result update(@RequestBody Label label) {
        labelService.update(label);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable("id") String id) {
        labelService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

   @RequestMapping(value = "/search",method = RequestMethod.POST)
    public Result findSearch(Map searchMap){
        List<Label> labelList = labelService.findSearch(searchMap);
        return new Result(true,StatusCode.OK,"查询成功",labelList);
    }

    @RequestMapping(value = "/search/{page}/{size}")
    public Result findSearch(Map searchMap,@PathVariable Integer page,@PathVariable Integer size){
        Page<Label> pageBean = labelService.findSearch(searchMap,page,size);
        return new Result(true,StatusCode.OK,"查询成功",new PageResult<>(pageBean.getTotalElements(),pageBean.getContent()));
    }
}
