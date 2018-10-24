package com.foyoedu.consumer.controller;

import com.foyoedu.common.pojo.Dept;
import com.foyoedu.common.service.DetpClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/feign")
public class DeptFeignController {

    @Autowired
    private DetpClientService service;

    @PostMapping(value = "/dept/add")
    public Long add(Dept dept) {
        return service.addDept(dept);
    }

    @GetMapping(value = "/dept/get/{id}")
    public Dept get(@PathVariable("id") Long id) {
        return service.getDept(id);
    }

    //要求前端必须content-type="application/json"
    //请求体必须json数据
    @SuppressWarnings("unchecked")
    @PostMapping(value = "/dept/list")
    public Map<String, Object> list(@RequestBody Map<String, String> map) {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        if(map.size() > 0){
            for (Map.Entry<String, String> entry : map.entrySet()){
                paramMap.add(entry.getKey(), entry.getValue());
            }
        }
        return service.listDeptTest(paramMap);
    }

    @PostMapping(value = "/dept/delete")
    public boolean deleteDeptById(@RequestParam("id") Long id) {
        return service.deleteDeptById(id);
    }

    @PostMapping(value = "/dept/delete/test")
    public boolean delete(Dept dept) {
        return service.delete(dept);
    }

    @PostMapping(value = "/dept/put/test")
    public boolean updateDept(Dept dept) {
        return service.updateDept(dept);
    }

    @GetMapping(value = "/hello")
    public String hello() {
        return service.hello();
    }

//    @GetMapping(value = "/dept/hello")
//    public String hello2() {
//        return restTemplate.getForObject(REST_URL_PREFIX + "/dept/hello", String.class);
//    }
}
