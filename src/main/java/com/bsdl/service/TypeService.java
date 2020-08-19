package com.bsdl.service;

import com.bsdl.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public interface TypeService {

    // 新增分类
    Type saveType(Type type);

    // 查询分类
    Type getType(Long id);

    // 通过名称查询分类
    Type getTypeByName(String name);

    // 分页查询
    Page<Type> listType(Pageable pageable);


    List<Type> listTypeTop(Integer size);

    // 返回所有分类
    List<Type> listType();

    // 修改分类
    Type updateType(Long id, Type type);

    // 删除分类
    void deleteType(Long id);


}
