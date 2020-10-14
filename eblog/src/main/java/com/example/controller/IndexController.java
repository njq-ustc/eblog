package com.example.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletRequest;

@Controller
public class IndexController extends BaseController{

    @RequestMapping({"", "/", "index"})
    public String index() {

        //当前页
        int pn = ServletRequestUtils.getIntParameter(req,"pn",1);
        //每一页记录数
        int size = ServletRequestUtils.getIntParameter(req,"size",2);
        Page page = new Page(pn,size);

        //参数（分页信息  分类  置顶  用户  精选  排序）
        IPage results = postService.paging(page,null,null,null,null,"created");

        req.setAttribute("pageData",results);
        req.setAttribute("currentCategoryId",0);
        return "index";
    }
}
