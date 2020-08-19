package com.bsdl.service;

import com.bsdl.po.Blog;
import com.bsdl.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {

    // 查询博客
    Blog getBlog(Long id);

    Blog getAndConvert(Long id);

    // 分页查询
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    // 分页对象查询所有博客
    Page<Blog> listBlog(Pageable pageable);

    Page<Blog> listBlog(String query, Pageable pageable);

    Page<Blog> listBlog(Long tagId, Pageable pageable);

    // 新增博客
    Blog saveBlog(Blog blog);

    // 修改博客
    Blog updateBlog(Long id, Blog blog);

    // 删除博客
    void deleteBlog(Long id);

    Long countBlog();

    Map<String, List<Blog>> archiveBlog();

    List<Blog> listRecommendBlogTop(Integer size);
}
