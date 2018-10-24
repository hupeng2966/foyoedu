package com.foyoedu.base.controller;

import com.foyoedu.base.utils.FoyoUtils;
import com.foyoedu.common.pojo.Dept;
import com.foyoedu.base.service.DeptService;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Accessors(chain=true)
public class DeptController {

    @Autowired
    private DeptService deptService;

    @PostMapping(value = "/dept/add")
    public Long addDept(@RequestBody Dept dept) throws Exception {
        String insertColumn = "dname,db_source";
        String values = "'" + dept.getDname() + "',DATABASE()";
        deptService.add(dept, insertColumn, values);
        return dept.getDeptno();
    }

    @GetMapping(value = "/dept/get/{id}")
    public Dept getDept(@PathVariable("id") Long id) throws Exception {
        return deptService.get(id);
    }

    @PostMapping(value = "/dept/list/test")
    public Map<String, Object> listDeptTest(@RequestBody MultiValueMap<String, String> paramMap) throws Exception {
        String filterCondition = "";
        String sortCondition = "";
        if (paramMap.containsKey("deptno") && paramMap.get("deptno").get(0).isEmpty()) {
            filterCondition = "where deptno >" + Integer.parseInt(paramMap.get("deptno").get(0));
        }
        if (paramMap.containsKey("sort") && paramMap.get("sort").get(0).isEmpty()) {
            sortCondition = "order by " + paramMap.get("sort").get(0);
        }

        return FoyoUtils.pageData(paramMap, deptService, filterCondition, sortCondition);
    }

    @GetMapping(value = "/dept/delete/{id}")
    public boolean deleteDeptById(@PathVariable("id") Long id) {
        return deptService.deleteById(id);
    }

    @PostMapping(value = "/dept/delete/test")
    public boolean delete(@RequestBody Dept dept) throws Exception {
        String filterCondition = "dname='" + dept.getDname() + "'";
        return deptService.delete(filterCondition);
    }

    @PostMapping(value = "/dept/put/test")
    public boolean updateDept(@RequestBody Dept dept) throws Exception {
        String setCondition = "dname='" + dept.getDname() + "',db_source=DATABASE()";
        String filterCondition = "deptno=" + dept.getDeptno();
        return deptService.update(setCondition, filterCondition);
    }

    @GetMapping(value = "/dept/hello")
    public String hello() {
        return "hello8001";
    }
}
