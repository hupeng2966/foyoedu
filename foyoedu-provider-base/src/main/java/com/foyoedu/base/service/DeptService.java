package com.foyoedu.base.service;

import com.foyoedu.common.pojo.Dept;
import com.foyoedu.common.pojo.PageResult;

import java.util.Map;

public interface DeptService extends BaseService<Dept> {

    public Long addDept(Dept dept) throws Throwable;

    public PageResult listDeptTest(Map<String, String> map) throws Throwable;

    public boolean deleteDept(Dept dept) throws Throwable;

    public boolean updateDept(Dept dept) throws Throwable;
}
