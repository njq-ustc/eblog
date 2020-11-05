package com.example.controller;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.common.lang.Result;
import com.example.entity.Post;
import com.example.entity.UserCollection;
import com.example.vo.CommentVo;
import com.example.vo.PostVo;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
public class PostController extends BaseController{

    @GetMapping("/category/{id:\\d}")
    public String category(@PathVariable(name = "id") Long id){
        int pn = ServletRequestUtils.getIntParameter(req,"pn",1);

        req.setAttribute("currentCategoryId",id);
        req.setAttribute("pn",pn);
        return "post/category";
    }

    @GetMapping("/post/{id:\\d*}")
    public String detail(@PathVariable(name = "id") Long id){
        PostVo vo = postService.selectOnePost(new QueryWrapper<Post>().eq("p.id",id));
        Assert.notNull(vo,"文章已被删除！");
        postService.putViewCount(vo);

        //1、分页  2、文章  3、用户id 排序
        IPage<CommentVo> results = commentService.paging(getPage(), vo.getId(), null, "created");

        req.setAttribute("currentCategoryId",vo.getCategoryId());
        req.setAttribute("post",vo);
        req.setAttribute("pageData",results);
        return "post/detail";
    }

    /**
     * 判断用户是否收藏了文章
     * @param pid
     * @return
     */
    @ResponseBody
    @PostMapping("/collection/find/")
    public Result collectionFind(Long pid) {
        int count = collectionService.count(new QueryWrapper<UserCollection>()
                .eq("user_id", getProfileId())
                .eq("post_id", pid)
        );
        return Result.success(MapUtil.of("collection", count > 0 ));
    }

    @ResponseBody
    @PostMapping("/collection/add/")
    public Result collectionAdd(Long pid) {
        Post post = postService.getById(pid);

        Assert.isTrue(post != null, "改帖子已被删除");
        int count = collectionService.count(new QueryWrapper<UserCollection>()
                .eq("user_id", getProfileId())
                .eq("post_id", pid)
        );
        if(count > 0) {
            return Result.fail("你已经收藏");
        }

        UserCollection collection = new UserCollection();
        collection.setUserId(getProfileId());
        collection.setPostId(pid);
        collection.setCreated(new Date());
        collection.setModified(new Date());

        collection.setPostUserId(post.getUserId());

        collectionService.save(collection);
        return Result.success();
    }

    @ResponseBody
    @PostMapping("/collection/remove/")
    public Result collectionRemove(Long pid) {
        Post post = postService.getById(pid);
        Assert.isTrue(post != null, "改帖子已被删除");

        collectionService.remove(new QueryWrapper<UserCollection>()
                .eq("user_id", getProfileId())
                .eq("post_id", pid));

        return Result.success();
    }
}
