package com.foyoedu.base.service;

import java.util.List;

public interface BaseService<E> {

    public boolean add(E obj, String insertColumns, String values) throws Throwable;

    public E getById(Long id) throws Throwable;

    public boolean deleteById(Long id);

    public boolean delete(String filterCondition) throws Throwable;

    public List<E> list(Integer pageNo, Integer pageSize, String sortCondition, String filterCondition) throws Throwable;

    public boolean update(String setColumns, String filterCondition) throws Throwable;

    public Integer totalCount(String filterCondition) throws Throwable;
}
