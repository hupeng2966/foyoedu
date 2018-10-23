package com.foyoedu.consumer.controller;

import com.foyoedu.common.pojo.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
public class DeptController {
    private static final String REST_URL_PREFIX = "http://localhost:8001";
    //private static final String REST_URL_PREFIX = "http://MICROSERVICECLOUD-DEPT";

    /**
     * 使用 使用restTemplate访问restful接口非常的简单粗暴无脑。 (url, requestMap,
     * ResponseBean.class)这三个参数分别代表 REST请求地址、请求参数、HTTP响应转换被转换成的对象类型。
     */
    @Autowired
    private RestTemplate restTemplate;

    @PostMapping(value = "/dept/add")
    public Long add(Dept dept) {
        return restTemplate.postForObject(REST_URL_PREFIX + "/dept/add", dept, Long.class);
    }

    @GetMapping(value = "/dept/get/{id}")
    public Dept get(@PathVariable("id") Long id) {
        return restTemplate.getForObject(REST_URL_PREFIX + "/dept/get/" + id, Dept.class);
    }


    //要求前端必须content-type="application/json"
    //请求体必须json数据
    @SuppressWarnings("unchecked")
    @PostMapping(value = "/dept/list")
    public Map<String, Object> list(@RequestBody Map<String, String> map) {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        for (Map.Entry<String, String> entry : map.entrySet()){
            paramMap.add(entry.getKey(), entry.getValue());
        }
        return restTemplate.postForObject(REST_URL_PREFIX + "/dept/list/test", paramMap, Map.class);
    }

    @PostMapping(value = "/dept/delete")
    public boolean deleteDeptById(@RequestParam("id") Long id) {
        return restTemplate.getForObject(REST_URL_PREFIX + "/dept/delete/" + id, boolean.class);
    }

    @PostMapping(value = "/dept/delete/test")
    public boolean delete(Dept dept) {
        return restTemplate.postForObject(REST_URL_PREFIX + "/dept/delete/test", dept, boolean.class);
    }

    @PostMapping(value = "/dept/put/test")
    public boolean updateDept(Dept dept) {
        return restTemplate.postForObject(REST_URL_PREFIX + "/dept/put/test", dept, boolean.class);
    }


}
