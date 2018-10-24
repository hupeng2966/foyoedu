package com.foyoedu.common.service;

import com.foyoedu.common.pojo.Dept;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@FeignClient(value = "FOYOEDU-PROVIDER-BASE")
public interface DetpClientService {

    @PostMapping(value = "/dept/add")
    public Long addDept(@RequestBody Dept dept);

    @GetMapping(value = "/dept/get/{id}")
    public Dept getDept(@PathVariable("id") Long id);

    @PostMapping(value = "/dept/list/test")
    public Map<String, Object> listDeptTest(@RequestBody MultiValueMap<String, String> paramMap);

    @GetMapping(value = "/dept/delete/{id}")
    public boolean deleteDeptById(@PathVariable("id") Long id);

    @PostMapping(value = "/dept/delete/test")
    public boolean delete(@RequestBody Dept dept);

    @PostMapping(value = "/dept/put/test")
    public boolean updateDept(@RequestBody Dept dept);

    @GetMapping(value = "/dept/hello")
    public String hello();

}
