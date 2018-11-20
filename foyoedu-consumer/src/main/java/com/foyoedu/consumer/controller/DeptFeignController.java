package com.foyoedu.consumer.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.foyoedu.common.pojo.Dept;
import com.foyoedu.common.pojo.FoyoResult;
import com.foyoedu.common.service.DeptClientService;
import com.foyoedu.common.utils.FoyoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
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
    public FoyoResult add(Dept dept) {
        return service.addDept(dept);
    }

    @GetMapping(value = "/dept/get/{id}")
    public FoyoResult get(@PathVariable("id") Long id) {
        return service.getDept(id);
    }

    //要求前端必须content-type="application/json"
    //请求体必须json数据
    @SuppressWarnings("unchecked")
    @PostMapping(value = "/dept/list")
    public FoyoResult list(@RequestBody Map<String, String> map) {
//        for (Map.Entry<String, String> entry : map.entrySet()){
//            paramMap.add(entry.getKey(), entry.getValue());
//        }
        return service.listDeptTest(map);
    }

    @PostMapping(value = "/dept/delete")
    public FoyoResult deleteDeptById(@RequestParam("id") Long id) {
        return service.deleteDeptById(id);
    }

    @PostMapping(value = "/dept/delete/test")
    public FoyoResult delete(Dept dept) {
        return service.delete(dept);
    }

    @PostMapping(value = "/dept/put/test")
    public FoyoResult updateDept(Dept dept) {
        return service.updateDept(dept);
    }

    @RequestMapping(value = "/hello")
    public FoyoResult hello(){
        return FoyoUtils.ok("hello");
    }
}
