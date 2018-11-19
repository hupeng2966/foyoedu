package com.foyoedu.base.controller;

import com.foyoedu.base.service.DeptService;
import com.foyoedu.common.pojo.Dept;
import com.foyoedu.common.utils.FoyoUtils;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Accessors(chain=true)
public class DeptController {

    @Autowired
    private DeptService deptService;

    @PostMapping(value = "/dept/add")
    public String addDept(@RequestBody Dept dept) throws Throwable {
        deptService.addDept(dept);
        return FoyoUtils.ok(dept.getDeptno());
    }

    @GetMapping(value = "/dept/get/{id}")
    public String getDept(@PathVariable("id") Long id) throws Throwable {
        String token = FoyoUtils.getToken();
        return FoyoUtils.ok(deptService.getById(id));
    }

    @PostMapping(value = "/dept/list/test")
    public String listDeptTest(@RequestBody Map<String, String> map) throws Throwable {
        return FoyoUtils.ok(deptService.listDeptTest(map));
    }

    @PostMapping(value = "/dept/delete")
    public String deleteDeptById(@RequestParam("id") Long id) {
        return FoyoUtils.ok(deptService.deleteById(id));
    }

    @PostMapping(value = "/dept/delete/test")
    public String delete(@RequestBody Dept dept) throws Throwable {
        return FoyoUtils.ok(deptService.deleteDept(dept));
    }

    @PostMapping(value = "/dept/put/test")
    public String updateDept(@RequestBody Dept dept) throws Throwable {
        return FoyoUtils.ok(deptService.updateDept(dept));
    }

    @GetMapping(value = "/dept/hello")
    public String hello() {
        int i = 1/0;
        return FoyoUtils.ok(String.valueOf(i));
        //return "hello8001";
    }
}
