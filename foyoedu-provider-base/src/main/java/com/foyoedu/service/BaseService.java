package com.foyoedu.service;

import com.foyoedu.pojo.Dept;

import java.util.List;

public interface BaseService<E> {

    public boolean add(E obj, String insertColumns, String values) throws Exception;

    public E get(Long id) throws Exception;

    public boolean deleteById(Long id);

    public boolean delete(String filterCondition);

    public List<E> list(Integer pageNo, Integer pageSize, String sortCondition, String filterCondition) throws Exception;

    public boolean update(String setColumns, String filterCondition);

    public Integer totalCount(String filterCondition);
}
