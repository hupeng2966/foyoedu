package com.foyoedu.base.service;

import java.util.List;

public interface BaseService<E> {

    public boolean add(E obj, String insertColumns, String values) throws Exception;

    public E get(Long id) throws Exception;

    public boolean deleteById(Long id);

    public boolean delete(String filterCondition) throws Exception;;

    public List<E> list(Integer pageNo, Integer pageSize, String sortCondition, String filterCondition) throws Exception;

    public boolean update(String setColumns, String filterCondition) throws Exception;

    public Integer totalCount(String filterCondition) throws Exception;
}
