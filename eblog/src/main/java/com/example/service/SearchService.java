package com.example.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.search.mq.PostMqIndexMessage;
import com.example.vo.PostVo;
import org.springframework.stereotype.Component;

import java.util.List;

public interface SearchService {

    IPage search(Page page, String q);

    int initEsData(List<PostVo> records);

    void createOrUpdateIndex(PostMqIndexMessage message);

    void removeIndex(PostMqIndexMessage message);
}
