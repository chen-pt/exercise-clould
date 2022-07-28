package com.person.chenpt.server.bus.rabbitmq;

import com.person.chenpt.constans.RabbitConst;
import com.person.chenpt.core.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: chenpt
 * @Description:
 * @Date: Created in 2022-07-27 19:08
 * @Modified By:
 */
@RestController
@RequestMapping("/rabbit")
@Api(tags = "消息队列")
public class RabbitMqController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/send")
    @ApiOperation("direct队列发送")
    public Result sendMessage(){
        for(int i=1;i<=10;i++){
            rabbitTemplate.convertAndSend(RabbitConst.directExchange,RabbitConst.directRoutingKey,"hello test");
        }
        return Result.success();
    }

    @GetMapping("/sendFanout")
    @ApiOperation("fanout队列发送")
    public Result sendFanoutMessage(){
        for(int i=1;i<=10;i++){
            rabbitTemplate.convertAndSend(RabbitConst.fanoutExchange,null,"hello fanout test");
        }
        return Result.success("ok");
    }

    @GetMapping("/sendTopic")
    @ApiOperation("topic队列发送")
    public Result sendTopicMessage(){
        rabbitTemplate.convertAndSend(RabbitConst.topicExchange,"chenpt.order.test","hello order test");
        rabbitTemplate.convertAndSend(RabbitConst.topicExchange,"chenpt.sms","hello sms test");
        return Result.success("ok");
    }


}
