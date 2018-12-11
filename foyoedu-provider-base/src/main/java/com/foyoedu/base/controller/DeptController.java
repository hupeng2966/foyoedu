package com.foyoedu.base.controller;

import com.foyoedu.base.service.DeptService;
import com.foyoedu.common.pojo.Dept;
import com.foyoedu.common.pojo.FoyoResult;
import com.foyoedu.common.utils.FoyoUtils;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.Callable;

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
    public Callable<FoyoResult> getDept(@PathVariable("id") Long id) throws Throwable {
        Callable<FoyoResult> result = new Callable<FoyoResult>() {
            @Override
            public FoyoResult call() throws Exception {
                String token = FoyoUtils.getToken();
                try {
                    return FoyoUtils.ok(deptService.getById(id));
                } catch (Throwable throwable) {
                    return FoyoUtils.error(500,throwable.getMessage());
                }
            }
        };
        return result;
    }

    @PostMapping(value = "/dept/list/test")
    public FoyoResult listDeptTest(@RequestBody Map<String, String> map) throws Throwable {
        return FoyoUtils.ok(deptService.listDeptTest(map));
    }

    @PostMapping(value = "/dept/delete")
    public FoyoResult deleteDeptById(@RequestParam("id") Long id) {
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
