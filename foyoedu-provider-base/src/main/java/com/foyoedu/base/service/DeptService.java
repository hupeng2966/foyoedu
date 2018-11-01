package com.foyoedu.base.service;

import com.foyoedu.common.pojo.Dept;
import com.foyoedu.common.pojo.FoyoResult;
import com.foyoedu.common.pojo.PageResult;
import org.springframework.util.MultiValueMap;

public interface DeptService extends BaseService<Dept> {

    public Long addDept(Dept dept) throws Throwable;

    public PageResult listDeptTest(MultiValueMap<String, String> paramMap) throws Throwable;

    public boolean deleteDept(Dept dept) throws Throwable;

    public boolean updateDept(Dept dept) throws Throwable;
}
