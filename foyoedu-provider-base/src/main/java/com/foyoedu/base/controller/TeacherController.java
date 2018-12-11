package com.foyoedu.base.controller;

import com.foyoedu.base.component.DeferredResultHolder;
import com.foyoedu.base.service.TeacherService;
import com.foyoedu.common.pojo.FoyoResult;
import com.foyoedu.common.pojo.Teacher;
import com.foyoedu.common.utils.FoyoUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
public class TeacherController {
    @Autowired
    private TeacherService service;

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private AmqpAdmin amqpAdmin;

    @Autowired
    private DeferredResultHolder deferredResultHolder;

    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FoyoResult addTeacherData(@RequestPart MultipartFile file) throws Throwable {
        service.addTeacherData(file);
        return FoyoUtils.ok("ok");
    }

    @PostMapping("/export")
    public FoyoResult findTeacherData() throws Throwable {
        return FoyoUtils.ok(service.findTeacherData());
        //return FoyoUtils.ok(service.list("",""));
    }

    @PostMapping("/rabbitSend")
    public DeferredResult<FoyoResult> rabbitmqSend(@RequestParam("courseid") Integer courseId){
        Teacher teacher = new Teacher();
        teacher.setLoginId("hupeng");
        teacher.setUserName("胡芃");
        teacher.setPhone("13813865429");
        String orderNumber = RandomStringUtils.randomNumeric(8);
        DeferredResult<FoyoResult> result = new DeferredResult<>();
        deferredResultHolder.getMap().put(orderNumber, result);
        service.addUserChoiceCourse(courseId, orderNumber);
        return result;
    }

    @GetMapping("/rabbitRecived")
    public Map<String, Object> rabbitmqRecived() {
        return (Map<String,Object>) template.receiveAndConvert("foyo.CourseSelection");
    }

    /*@GetMapping("/rabbitDeclare")
    public void rabbitDeclare() {
        amqpAdmin.declareExchange(new DirectExchange("foyo.direct"));
        amqpAdmin.declareQueue(new Queue("foyo.queue"));
        amqpAdmin.declareBinding(new Binding("foyo.queue", Binding.DestinationType.QUEUE, "foyo.direct", "foyo.queue",null));
    }*/
}
