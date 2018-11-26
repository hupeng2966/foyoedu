package com.foyoedu.consumer.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.foyoedu.common.pojo.Dept;
import com.foyoedu.common.pojo.FoyoResult;
import com.foyoedu.common.service.DeptClientService;
import com.foyoedu.common.utils.FoyoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Map;
import java.util.concurrent.Callable;

@RestController
@RequestMapping(value = "/foyo")
public class DeptFeignController {

    @Autowired
    private DeptClientService service;

    @PostMapping(value = "/dept/add")
    public FoyoResult add(@Valid Dept dept/*, BindingResult errors*/) {
//        if(errors.hasErrors()) {
//            errors.getAllErrors().stream().forEach(error -> {
//                 FoyoUtils.outPutResponse(FoyoUtils.error(500,error.getDefaultMessage()));
//            });
//            return null;
//        }
        return service.addDept(dept);
    }

    @GetMapping(value = "/dept/get/{id:\\d+}")
    public Callable<FoyoResult> get(@PathVariable("id") Long id) {
        Callable<FoyoResult> result = new Callable<FoyoResult>() {
			@Override
			public FoyoResult call() throws Exception {
				return service.getDept(id);
			}
		};
        return result;
    }

//    public FoyoResult get(@PathVariable("id") Long id) {
//        return service.getDept(id);
//    }

    //要求前端必须content-type="application/json"
    //请求体必须json数据
    @SuppressWarnings("unchecked")
    @PostMapping(value = "/dept/list")
    public FoyoResult list(@RequestBody Map<String, String> map) {
//        for (Map.Entry<String, String> entry : map.entrySet()){
//            entry.getKey(), entry.getValue();
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

    //@RequestMapping(value = "/hello", produces = "text/plain;charset=UTF-8")
    @GetMapping("/hello")
    public FoyoResult hello() {
        int i = 1/0;
        return service.hello();
    }
}
