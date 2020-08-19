package com.bsdl.service;

import com.bsdl.NotFoundException;
import com.bsdl.dao.TagRepository;
import com.bsdl.po.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Transactional
    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }


    @Transactional
    @Override
    public Tag getTag(Long id) {
        return tagRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> listTag(String ids) {
        List<Long> idCollection = new ArrayList<>();
        if (!ids.isEmpty()) {
            String[] idArray = ids.split(",");
            for (String id: idArray) {
                idCollection.add(Long.valueOf(id));
            }
        }
        return tagRepository.findAllById(idCollection);
    }

    @Transactional
    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag tmp = tagRepository.findById(id).orElse(null);
        if (tmp==null) {
            throw new NotFoundException("不存在该标签");
        }
        BeanUtils.copyProperties(tag, tmp);
        return tagRepository.save(tmp);
    }

    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    @Override
    public List<Tag> listTagTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "blogs.size");
        Pageable pageable = PageRequest.of(0, size, sort);
        return tagRepository.findTop(pageable);
    }
}
