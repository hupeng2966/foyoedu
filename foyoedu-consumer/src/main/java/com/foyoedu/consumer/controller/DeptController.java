package com.foyoedu.consumer.controller;

import com.foyoedu.common.pojo.Dept;
import com.foyoedu.common.pojo.FoyoResult;
import com.foyoedu.common.service.DeptClientService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Map;
import java.util.concurrent.Callable;

@RestController
@RequestMapping(value = "/foyo")
public class DeptController {

    @Autowired
    private DeptClientService service;

    @PostMapping(value = "/dept/add")
    @ApiOperation("增加部门")
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
    @ApiOperation("根据部门id获取部门信息")
    public Callable<FoyoResult> get(@ApiParam(value = "部门id", allowEmptyValue = false) @PathVariable("id") Long id) {
        Callable<FoyoResult> result = new Callable<FoyoResult>() {
			@Override
			public FoyoResult call() throws Exception {
				return service.getDept(id);
			}
		};
        return result;
    }

    //要求前端必须content-type="application/json"
    //请求体必须json数据
    @SuppressWarnings("unchecked")
    @PostMapping(value = "/dept/list")
    @ApiOperation("根据自定义条件获取部门信息")
    public FoyoResult list(@RequestBody Map<String, String> map) {
//        for (Map.Entry<String, String> entry : map.entrySet()){
//            entry.getKey(), entry.getValue();
//        }
        return service.listDeptTest(map);
    }

    @PostMapping(value = "/dept/delete")
    @ApiOperation("根据部门id删除部门")
    public FoyoResult deleteDeptById(@ApiParam(value = "部门id", allowEmptyValue = false) @RequestParam("id") Long id) {
        return service.deleteDeptById(id);
    }

    @PostMapping(value = "/dept/delete/test")
    @ApiOperation("根据自定义条件删除部门")
    public FoyoResult delete(Dept dept) {
        return service.delete(dept);
    }

    @PostMapping(value = "/dept/put/test")
    @ApiOperation("更新部门信息")
    public FoyoResult updateDept(Dept dept) {
        return service.updateDept(dept);
    }

    //@RequestMapping(value = "/hello", produces = "text/plain;charset=UTF-8")
    @GetMapping("/hello")
    @ApiOperation("测试HelloWorld")
    public FoyoResult hello() {
        int i = 1/0;
        return service.hello();
    }
}
