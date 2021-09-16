package com.tensquare.search.controller;

import com.tensquare.search.pojo.Article;
import com.tensquare.search.service.ArticleService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "/search/{keyword}/{page}/{size}",method = RequestMethod.GET)
    public Result search(@PathVariable String keyword, @PathVariable Integer page ,@PathVariable Integer size){
        Page<Article> articlePage = articleService.findByTitleOrContentLike(keyword,page,size);
        return new Result(true, StatusCode.OK,"搜索成功", new PageResult< Article >(articlePage.getTotalElements(),articlePage.getContent()));
    }
}
