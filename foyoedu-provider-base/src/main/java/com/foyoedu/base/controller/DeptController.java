package com.foyoedu.base.controller;

import com.foyoedu.common.pojo.Dept;
import com.foyoedu.base.service.DeptService;
import com.foyoedu.common.pojo.FoyoResult;
import com.foyoedu.common.utils.FoyoUtils;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Accessors(chain=true)
public class DeptController {

    @Autowired
    private DeptService deptService;

    @PostMapping(value = "/dept/add")
    public FoyoResult addDept(@RequestBody Dept dept) throws Throwable {
        deptService.addDept(dept);
        return FoyoUtils.ok(dept.getDeptno());
    }

    @GetMapping(value = "/dept/get/{id}")
    public FoyoResult getDept(@PathVariable("id") Long id) throws Throwable {
        String token = FoyoUtils.getToken();
        return FoyoUtils.ok(deptService.getById(id));
    }

    @PostMapping(value = "/dept/list/test")
    public FoyoResult listDeptTest(@RequestBody MultiValueMap<String, String> paramMap) throws Throwable {
        return FoyoUtils.ok(deptService.listDeptTest(paramMap));
    }

    @GetMapping(value = "/dept/delete/{id}")
    public FoyoResult deleteDeptById(@PathVariable("id") Long id) {
        return FoyoUtils.ok(deptService.deleteById(id));
    }

    @PostMapping(value = "/dept/delete/test")
    public FoyoResult delete(@RequestBody Dept dept) throws Throwable {
        return FoyoUtils.ok(deptService.deleteDept(dept));
    }

    @PostMapping(value = "/dept/put/test")
    public FoyoResult updateDept(@RequestBody Dept dept) throws Throwable {
        return FoyoUtils.ok(deptService.updateDept(dept));
    }

    @GetMapping(value = "/dept/hello")
    public FoyoResult hello() {
        int i = 1/0;
        return FoyoUtils.ok(String.valueOf(i));
        //return "hello8001";
    }
}
