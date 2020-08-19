package com.bsdl.controller;

import com.bsdl.po.Tag;
import com.bsdl.service.BlogService;
import com.bsdl.service.TagService;
import com.bsdl.service.TypeService;
import com.bsdl.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @program: blog
 * @create: 2020-07-28 21:03
 **/

@Controller
public class TagShowController {

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/tags/{id}")
    public String types(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                                Pageable pageable, @PathVariable Long id, Model model) {
        List<Tag> tags = tagService.listTagTop(10000);
        if (id == -1) {
            if (!tags.isEmpty()) {
                id = tags.get(0).getId();
            }
        }
        model.addAttribute("tags", tags);
        model.addAttribute("page", blogService.listBlog(id, pageable));
        model.addAttribute("activeTagId", id);
        return "tags";
    }

}
