package com.summer.cat.service.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Strings;
import com.summer.cat.entity.BlogArticle;
import com.summer.cat.entity.BlogArticleTag;
import com.summer.cat.entity.BlogTag;
import com.summer.cat.mapper.BlogArticleMapper;
import com.summer.cat.service.service.IBlogArticleService;
import com.summer.cat.service.service.IBlogArticleTagService;
import com.summer.cat.service.service.IBlogHisTagService;
import com.summer.cat.service.service.IBlogTagService;
import com.summer.cat.util.CatsException;
import com.summer.cat.util.GsonUtil;
import com.summer.cat.vo.UserRoleVo;

/**
 * <p>
 * 文章表 服务实现类
 * </p>
 *
 * @author SummerCat
 * @since 2020-04-17
 */
@Service
public class BlogArticleServiceImpl extends ServiceImpl<BlogArticleMapper, BlogArticle> implements IBlogArticleService {
    final IBlogArticleTagService articleTagService;

    final IBlogTagService tagService;

    final IBlogHisTagService hisTagService;

    public BlogArticleServiceImpl(IBlogArticleTagService articleTagService, IBlogTagService tagService,
            IBlogHisTagService hisTagService) {
        this.articleTagService = articleTagService;
        this.tagService = tagService;
        this.hisTagService = hisTagService;
    }

    @Override
    public QueryWrapper<BlogArticle> buildWrapper(BlogArticle var) {
        return null;
    }

    /**
     * 创建博文 增加tagList 关联之间关系 存放用户历史tag
     *
     * @param passageJSON
     * @param tagsJSON
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createPassage(String passageJSON, String tagsJSON, UserRoleVo userRoleVo) {
        if (Strings.isNullOrEmpty(passageJSON)) {
            throw new CatsException("文章数据不可为空");
        }
        BlogArticle blogArticle = GsonUtil.gson2Bean(passageJSON, BlogArticle.class);
        List<BlogTag> blogTagList = new ArrayList<>();
        if (!Strings.isNullOrEmpty(tagsJSON)) {
            blogTagList = GsonUtil.gson2List(tagsJSON, BlogTag.class);
        }
        this.save(blogArticle);
        for (BlogTag blogTag : blogTagList) {
            tagService.insertTagByIgnoreIfExistTagName(blogTag);
            // tag 关联
            BlogArticleTag articleTag = new BlogArticleTag();
            articleTag.setBatBlogTagId(blogTag.getId());
            articleTag.setBatBlogArticleId(blogArticle.getId());
            articleTag.setBatBlogTagName(blogTag.getBtTagName());
            articleTag.setBatCreateTime(new Date());
            articleTagService.save(articleTag);
        }
        hisTagService.addTagHisList(blogTagList, userRoleVo);
    }
}
