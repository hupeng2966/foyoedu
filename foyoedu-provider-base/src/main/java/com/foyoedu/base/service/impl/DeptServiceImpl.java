package com.foyoedu.base.service.impl;

import com.foyoedu.base.service.BaseService;
import com.foyoedu.base.service.DeptService;
import com.foyoedu.base.utils.Utils;
import com.foyoedu.common.pojo.Dept;
import com.foyoedu.common.pojo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

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

    public PageResult listDeptTest(Map<String, String> map) throws Throwable {
        String filterCondition = "";
        String sortCondition = "";
        if (map.containsKey("deptno") && !map.get("deptno").isEmpty()) {
            filterCondition = "where deptno >" + Integer.parseInt(map.get("deptno"));
        }
        if (map.containsKey("sort") && !map.get("sort").isEmpty()) {
            sortCondition = "order by " + map.get("sort");
        }
        return Utils.pageData(map, Service, filterCondition, sortCondition);
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
