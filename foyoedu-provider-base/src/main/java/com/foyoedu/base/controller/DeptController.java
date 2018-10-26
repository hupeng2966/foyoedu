package com.foyoedu.base.controller;

import com.foyoedu.base.utils.Utils;
import com.foyoedu.common.pojo.Dept;
import com.foyoedu.base.service.DeptService;
import com.foyoedu.common.pojo.FoyoResult;
import com.foyoedu.common.utils.FoyoUtils;
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
    public FoyoResult addDept(@RequestBody Dept dept) throws Exception {
        String insertColumn = "dname,db_source";
        String values = "'" + dept.getDname().replaceAll("'","''") + "',DATABASE()";
        deptService.add(dept, insertColumn, values);
        return FoyoUtils.ok(dept.getDeptno());
    }

    @GetMapping(value = "/dept/get/{id}")
    public FoyoResult getDept(@PathVariable("id") Long id) throws Exception {
        return FoyoUtils.ok(deptService.get(id));
    }

    @PostMapping(value = "/dept/list/test")
    public FoyoResult listDeptTest(@RequestBody MultiValueMap<String, String> paramMap) throws Exception {
        String filterCondition = "";
        String sortCondition = "";
        if (paramMap.containsKey("deptno") && paramMap.get("deptno").get(0).isEmpty()) {
            filterCondition = "where deptno >" + Integer.parseInt(paramMap.get("deptno").get(0));
        }
        if (paramMap.containsKey("sort") && paramMap.get("sort").get(0).isEmpty()) {
            sortCondition = "order by " + paramMap.get("sort").get(0);
        }

        return FoyoUtils.ok(Utils.pageData(paramMap, deptService, filterCondition, sortCondition));
    }

    @GetMapping(value = "/dept/delete/{id}")
    public FoyoResult deleteDeptById(@PathVariable("id") Long id) {
        return FoyoUtils.ok(deptService.deleteById(id));
    }

    @PostMapping(value = "/dept/delete/test")
    public FoyoResult delete(@RequestBody Dept dept) throws Exception {
        String filterCondition = "dname='" + dept.getDname().replaceAll("'","''") + "'";
        return FoyoUtils.ok(deptService.delete(filterCondition));
    }

    @PostMapping(value = "/dept/put/test")
    public FoyoResult updateDept(@RequestBody Dept dept) throws Exception {
        String setCondition = "dname='" + dept.getDname().replaceAll("'","''") + "',db_source=DATABASE()";
        String filterCondition = "deptno=" + dept.getDeptno();
        return FoyoUtils.ok(deptService.update(setCondition, filterCondition));
    }

    @GetMapping(value = "/dept/hello")
    public FoyoResult hello() {
        int i = 1/0;
        return FoyoUtils.ok(String.valueOf(i));
        //return "hello8001";
    }
}
