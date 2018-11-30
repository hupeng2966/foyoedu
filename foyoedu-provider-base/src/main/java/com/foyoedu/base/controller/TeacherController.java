package com.foyoedu.base.controller;

import com.foyoedu.base.service.TeacherService;
import com.foyoedu.common.pojo.FoyoResult;
import com.foyoedu.common.pojo.Teacher;
import com.foyoedu.common.utils.FoyoUtils;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class TeacherController {
    @Autowired
    private TeacherService service;

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private AmqpAdmin amqpAdmin;

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

    @GetMapping("/rabbitSend")
    public void rabbitmqSend(){
        Teacher teacher = new Teacher();
        teacher.setUserName("胡芃");
        teacher.setPhone("13813865429");
        template.convertAndSend("exchange.direct","foyo.CourseSelection",teacher);
    }

    @GetMapping("/rabbitRecived")
    public Teacher rabbitmqRecived() {
        return (Teacher) template.receiveAndConvert("foyo.CourseSelection");
    }

    @GetMapping("/rabbitDeclare")
    public void rabbitDeclare() {
        amqpAdmin.declareExchange(new DirectExchange("foyo.direct"));
        amqpAdmin.declareQueue(new Queue("foyo.queue"));
        amqpAdmin.declareBinding(new Binding("foyo.queue", Binding.DestinationType.QUEUE, "foyo.direct", "foyo.queue",null));
    }
}
