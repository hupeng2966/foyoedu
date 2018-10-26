package com.foyoedu.common.service;

import com.foyoedu.common.pojo.Dept;
import com.foyoedu.common.pojo.FoyoResult;
import com.foyoedu.common.service.hystrix.DeptClientServiceFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@FeignClient(value = "FOYOEDU-PROVIDER-BASE", fallbackFactory = DeptClientServiceFactory.class)
//@FeignClient(value = "FOYOEDU-PROVIDER-BASE")
public interface DeptClientService {

    @PostMapping(value = "/dept/add")
    public FoyoResult addDept(@RequestBody Dept dept);

    @GetMapping(value = "/dept/get/{id}")
    public FoyoResult getDept(@PathVariable("id") Long id);

    @PostMapping(value = "/dept/list/test")
    public FoyoResult listDeptTest(@RequestBody MultiValueMap<String, String> paramMap);

    @GetMapping(value = "/dept/delete/{id}")
    public FoyoResult deleteDeptById(@PathVariable("id") Long id);

    @PostMapping(value = "/dept/delete/test")
    public FoyoResult delete(@RequestBody Dept dept);

    @PostMapping(value = "/dept/put/test")
    public FoyoResult updateDept(@RequestBody Dept dept);

    @GetMapping(value = "/dept/hello")
    public FoyoResult hello();

}
