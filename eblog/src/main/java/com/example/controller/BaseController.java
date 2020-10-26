package com.example.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.service.CommentService;
import com.example.service.PostService;
import com.example.shiro.AccountProfile;
import org.apache.catalina.security.SecurityUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;

@Controller
public class BaseController {

    @Autowired
    HttpServletRequest req;

    @Autowired
    PostService postService;

    @Autowired
    CommentService commentService;

    public Page getPage(){
        //当前页
        int pn = ServletRequestUtils.getIntParameter(req,"pn",1);
        //每一页记录数
        int size = ServletRequestUtils.getIntParameter(req,"size",2);
        return new Page(pn,size);
    }

    protected AccountProfile getProfile() {
        return (AccountProfile)SecurityUtils.getSubject().getPrincipal();
    }

    protected Long getProfileId() {
        return getProfile().getId();
    }
}
