package com.person.chenpt.controller;

import com.person.chenpt.constant.MessageQueueConstants;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: chenpt
 * @Description:
 * @Date: Created in 2021-11-09 15:41
 * @Modified By:
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Value("${person.name}")
    private String personName;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/str")
    public String testStr(){

        return personName;
    }

    @GetMapping("/sendMq")
    public String sendMq(){
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "test message, hello!";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String,Object> map=new HashMap<>();
        map.put("messageId",messageId);
        map.put("messageData",messageData);
        map.put("createTime",createTime);
        //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
        rabbitTemplate.convertAndSend(MessageQueueConstants.DEFAULT_DIRECT_EXCHANGE, "default.direct.key", map);
        return "ok";
    }


}
