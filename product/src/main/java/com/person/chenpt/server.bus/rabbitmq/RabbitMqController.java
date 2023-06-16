package com.person.chenpt.server.bus.rabbitmq;

import com.person.chenpt.constans.RabbitConst;
import com.person.chenpt.core.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.core.MessageDeliveryMode;
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
        rabbitTemplate.convertAndSend(RabbitConst.directExchange,RabbitConst.directRoutingKey,"hello test");
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

    @GetMapping("/testAck")
    @ApiOperation("ack测试")
    public Result testAck(){
        /**
         * 测试交换机不存在的情况下消息回调机制
         * error: reply-code=404, reply-text=NOT_FOUND - no exchange 'tests' in vhost '/', class-id=60, method-id=40
         */
//        rabbitTemplate.convertAndSend("tests","chenpt.order.test","hello order test");
        /**
         * 交换机存在、没有相应的路由键绑定、两个回调函数都触发了
         * 消息是推送成功到服务器了的，所以ConfirmCallback对消息确认情况是true；
         * 而在RetrunCallback回调函数的打印参数里面可以看到，消息是推送到了交换机成功了，
         * 但是在路由分发给队列的时候，找不到队列，所以报了错误 NO_ROUTE 。
         */
        rabbitTemplate.convertAndSend(RabbitConst.directExchange,"chenpt.order.test","hello order test");
        return Result.success("ok");
    }

    /**
     * 发送带有过期时间的消息 (10s)
     * 超时未消费的消息则进入死信队列
     * @return
     */
    @GetMapping("/sendDlx")
    @ApiOperation("dlx测试")
    public Result sendDlx(){
        rabbitTemplate.convertAndSend("testDirectEx","test","hello deadEx test",message -> {
            message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            message.getMessageProperties().setExpiration("10000");
            return message;
        });
        return Result.success("ok");
    }
}
