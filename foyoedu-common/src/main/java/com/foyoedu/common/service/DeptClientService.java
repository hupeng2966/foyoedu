package com.foyoedu.common.service;

import com.foyoedu.common.pojo.Dept;
import com.foyoedu.common.pojo.FoyoResult;
import com.foyoedu.common.service.hystrix.DeptClientServiceFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

//@FeignClient(value = "FOYOEDU-PROVIDER-BASE", fallbackFactory = DeptClientServiceFactory.class)
@FeignClient(value = "FOYOEDU-ZUUL", fallbackFactory = DeptClientServiceFactory.class)
@RequestMapping(value = "/base")
public interface DeptClientService {

    @PostMapping(value = "/dept/add")
    public FoyoResult addDept(@RequestBody Dept dept);

    @GetMapping(value = "/dept/get/{id}")
    public FoyoResult getDept(@PathVariable("id") Long id);

    @PostMapping(value = "/dept/list/test")
    public FoyoResult listDeptTest(@RequestBody Map<String, String> map);

    @PostMapping(value = "/dept/delete")
    public FoyoResult deleteDeptById(@RequestParam("id") Long id);

    @PostMapping(value = "/dept/delete/test")
    public FoyoResult delete(@RequestBody Dept dept);

    @PostMapping(value = "/dept/put/test")
    public FoyoResult updateDept(@RequestBody Dept dept);

    @GetMapping(value = "/dept/hello")
    public FoyoResult hello();

}
