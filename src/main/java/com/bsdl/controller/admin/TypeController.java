package com.bsdl.controller.admin;

import com.bsdl.po.Type;
import com.bsdl.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public String types(@PageableDefault(size = 8, sort = {"id"}, direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model) {
        model.addAttribute("page", typeService.listType(pageable));
        return "admin/types";
    }

    @GetMapping("/types/release")
    public String release(Model model) {
        model.addAttribute("type", new Type());
        return "admin/types-release";
    }

    @GetMapping("/types/{id}/release")
    public String editRelease(@PathVariable Long id, Model model) {
        model.addAttribute("type", typeService.getType(id));
        return "admin/types-release";
    }

    @PostMapping("/types")
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes) {
        Type tmp = typeService.getTypeByName(type.getName());
        if(tmp != null) {
            result.rejectValue("name","nameError", "这个分类已经添加过了");
        }

        if (result.hasErrors()) {
            return "admin/types-release";
        }
        Type type1 = typeService.saveType(type);
        if (type1 == null) {
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/types";
    }


    @PostMapping("/types/{id}")
    public String editPost(@Valid Type type, BindingResult result, @PathVariable Long id, RedirectAttributes attributes) {
        Type tmp = typeService.getTypeByName(type.getName());
        if(tmp != null) {
            result.rejectValue("name","nameError", "这个分类已经添加过了");
        }

        if (result.hasErrors()) {
            return "admin/types-release";
        }
        Type type1 = typeService.updateType(id, type);
        if (type1 == null) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
//        Type type = typeService.getType(id);
//        if (type == null) {
//            return "admin/types";
//        }
        typeService.deleteType(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/types";
    }
}
