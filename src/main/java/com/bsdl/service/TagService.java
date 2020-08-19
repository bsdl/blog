package com.bsdl.service;

import com.bsdl.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
public interface TagService  {

    // 新增标签
    Tag saveTag(Tag tag);

    // 查询标签
    Tag getTag(Long id);

    // 分页查询
    Page<Tag> listTag(Pageable pageable);

    // 获取所有的标签
    List<Tag> listTag();

    // 根据多个id获取标签 "id1, id2, id3......"
    List<Tag> listTag(String ids);

    // 修改标签
    Tag updateTag(Long id, Tag tag);

    // 删除标签
    void deleteTag(Long id);

    // 通过标签名称查询标签
    Tag getTagByName(String name);

    List<Tag> listTagTop(Integer size);
}
