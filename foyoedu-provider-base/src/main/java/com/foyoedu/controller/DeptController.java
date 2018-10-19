package com.foyoedu.controller;

import com.foyoedu.pojo.Dept;
import com.foyoedu.service.DeptService;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Accessors(chain=true)
public class DeptController {

    @Autowired
    private DeptService deptService;

    @PostMapping(value = "/dept/add")
    public Long addDept(Dept dept) throws Exception {
        String insertColumn = "dname,db_source";
        String values = "'" + dept.getDname() + "',DATABASE()";
        deptService.add(dept, insertColumn, values);
        return dept.getDeptno();
    }

    @GetMapping(value = "/dept/get/{id}")
    public Dept getDept(@PathVariable("id") Long id) throws Exception {
        return deptService.get(id);
    }

    @GetMapping(value = "/dept/list/test")
    public Map<String, Object> listDeptTest(Integer pageNo, Integer pageSize) throws Exception {
        String filterCondition = "where deptno > 2";
        String sortCondition = "order by dname";

        Map<String, Object> map = new HashMap<>();
        Integer total = deptService.totalCount(filterCondition);
        map.put("total", total);
        if(total > 0) {
            List<Dept> list = deptService.list(pageNo, pageSize, filterCondition, sortCondition);
            map.put("data", list);
        }
        return map;
    }

    @DeleteMapping(value = "/dept/delete/{id}")
    public boolean deleteDeptById(@PathVariable("id") Long id) {
        return deptService.deleteById(id);
    }

    @DeleteMapping(value = "/dept/delete/test")
    public boolean delete(Dept dept) {
        String filterCondition = "dname='" + dept.getDname() + "'";
        return deptService.delete(filterCondition);
    }

    @PutMapping(value = "/dept/put/test")
    public boolean updateDept(Dept dept) {
        String setCondition = "dname='" + dept.getDname() + "',db_source=DATABASE()";
        String filterCondition = "deptno=" + dept.getDeptno();
        return deptService.update(setCondition, filterCondition);
    }

}
