package com.foyoedu.common.service;

import com.foyoedu.common.pojo.Dept;
import com.foyoedu.common.service.hystrix.DeptClientServiceFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

//@FeignClient(value = "FOYOEDU-PROVIDER-BASE", fallbackFactory = DeptClientServiceFactory.class)
@FeignClient(value = "FOYOEDU-ZUUL", fallbackFactory = DeptClientServiceFactory.class)
@RequestMapping(value = "/base")
public interface DeptClientService {

    @PostMapping(value = "/dept/add")
    public String addDept(@RequestBody Dept dept);

    @GetMapping(value = "/dept/get/{id}")
    public String getDept(@PathVariable("id") Long id);

    @PostMapping(value = "/dept/list/test")
    public String listDeptTest(@RequestBody MultiValueMap<String, String> paramMap);

    @PostMapping(value = "/dept/delete")
    public String deleteDeptById(@RequestParam("id") Long id);

    @PostMapping(value = "/dept/delete/test")
    public String delete(@RequestBody Dept dept);

    @PostMapping(value = "/dept/put/test")
    public String updateDept(@RequestBody Dept dept);

    @GetMapping(value = "/dept/hello")
    public String hello();

}
