package com.foyoedu.consumer.controller;

import com.foyoedu.common.pojo.Dept;
import com.foyoedu.common.service.DeptClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping(value = "/foyo")
public class DeptFeignController {

    @Autowired
    private DeptClientService service;

    @PostMapping(value = "/dept/add")
    public String add(Dept dept) {
        return service.addDept(dept);
    }

    @GetMapping(value = "/dept/get/{id}")
    public String get(@PathVariable("id") Long id) {
        return service.getDept(id);
    }

    //要求前端必须content-type="application/json"
    //请求体必须json数据
    @SuppressWarnings("unchecked")
    @PostMapping(value = "/dept/list")
    public String list(@RequestBody Map<String, String> map) {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        if(map.size() > 0){
            for (Map.Entry<String, String> entry : map.entrySet()){
                paramMap.add(entry.getKey(), entry.getValue());
            }
        }
        return service.listDeptTest(paramMap);
    }

    @PostMapping(value = "/dept/delete")
    public String deleteDeptById(@RequestParam("id") Long id) {
        return service.deleteDeptById(id);
    }

    @PostMapping(value = "/dept/delete/test")
    public String delete(Dept dept) {
        return service.delete(dept);
    }

    @PostMapping(value = "/dept/put/test")
    public String updateDept(Dept dept) {
        return service.updateDept(dept);
    }

    @GetMapping(value = "/hello")
    public String hello(){
        return service.hello();
    }
}
