package com.foyoedu.base.service.impl;

import com.foyoedu.base.service.BaseService;
import com.foyoedu.base.service.DeptService;
import com.foyoedu.base.utils.Utils;
import com.foyoedu.common.pojo.Dept;
import com.foyoedu.common.pojo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

@Service
public class DeptServiceImpl extends BaseServiceImpl<Dept> implements DeptService {

    public DeptServiceImpl() {
        super("dept", "deptno", Dept.class);
    }

    @Autowired
    private BaseService<Dept> Service;

    public Long addDept(Dept dept) throws Throwable {
        String insertColumn = "dname,db_source";
        String values = "'" + dept.getDname().replaceAll("'","''") + "',DATABASE()";
        Service.add(dept, insertColumn, values);
        return dept.getDeptno();
    }

    public PageResult listDeptTest(MultiValueMap<String, String> paramMap) throws Throwable {
        String filterCondition = "";
        String sortCondition = "";
        if (paramMap.containsKey("deptno") && paramMap.get("deptno").get(0).isEmpty()) {
            filterCondition = "where deptno >" + Integer.parseInt(paramMap.get("deptno").get(0));
        }
        if (paramMap.containsKey("sort") && paramMap.get("sort").get(0).isEmpty()) {
            sortCondition = "order by " + paramMap.get("sort").get(0);
        }
        return Utils.pageData(paramMap, Service, filterCondition, sortCondition);
    }

    public boolean deleteDept(Dept dept) throws Throwable {
        String filterCondition = "dname='" + dept.getDname().replaceAll("'","''") + "'";
        return Service.delete(filterCondition);
    }

    public boolean updateDept(Dept dept) throws Throwable {
        String setCondition = "dname='" + dept.getDname().replaceAll("'","''") + "',db_source=DATABASE()";
        String filterCondition = "deptno=" + dept.getDeptno();
        return Service.update(setCondition, filterCondition);
    }
}
