package com.bsdl.controller.admin;


import com.bsdl.po.Blog;
import com.bsdl.po.Tag;
import com.bsdl.po.Type;
import com.bsdl.po.User;
import com.bsdl.service.BlogService;
import com.bsdl.service.TagService;
import com.bsdl.service.TypeService;
import com.bsdl.vo.BlogQuery;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.regex.Pattern;


@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String RELEASE = "admin/blog-release";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/blogs")
    public String list(@PageableDefault(size = 3, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                                   Pageable pageable, BlogQuery blog, Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return LIST;
    }

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 3, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                               Pageable pageable, BlogQuery blog, Model model) {
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return "admin/blogs :: blogList";
    }

    @GetMapping("/blogs/release")
    public String release(Model model) {
        model.addAttribute("blog", new Blog());
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
        return RELEASE;
    }

    @GetMapping("/blogs/{id}/release")
    public String editRelease(@PathVariable Long id, Model model) {
        Blog blog = blogService.getBlog(id);
        blog.initTagIds();
        model.addAttribute("blog", blog);

        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
        return RELEASE;
    }

    @PostMapping("/blogs")
    public String post(Blog blog, HttpSession session, RedirectAttributes attributes) {
        blog.setUser((User) session.getAttribute("user"));
        // 给博客设置分类和标签
        blog.setType(typeService.getType(blog.getType().getId()));
        String tagIds = blog.getTagIds();
//        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
//        boolean isLong = pattern.matcher(idOrName).matches();
        blog.setTags(tagService.listTag(tagIds));
        Blog blog1;
        if (blog.getId() == null) {
            blog1 = blogService.saveBlog(blog);
        } else {
            blog1 = blogService.updateBlog(blog.getId(), blog);
        }

        if (blog1 == null) {
            attributes.addFlashAttribute("message", "发布失败");
        } else {
            attributes.addFlashAttribute("message", "发布成功");
        }
        return REDIRECT_LIST;
    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message", "删除成功");
        return REDIRECT_LIST;
    }

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    public static void main(String[] args) {
        String t = "wocao";
        Boolean a = isInteger(t);
        System.out.println(a);
    }
}
