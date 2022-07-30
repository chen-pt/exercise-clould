package com.person.chenpt.config.rabbit;

import com.person.chenpt.constans.RabbitConst;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 死信队列--Dead-Letter-Exchange，死信交换器
 * 消息在一个队列中变成死信（Dead Letter）之后，被重新发送到一个特殊的交换器（DLX）中，同时，绑定DLX的队列就称为“死信队列”。
 * 以下几种情况导致消息变为死信：
 * 消息被拒绝（Basic.Reject/Basic.Nack），并且设置requeue参数为false；
 * 消息过期；
 * 队列达到最大长度。
 * @Author: chenpt
 * @Description:
 * @Date: Created in 2022-07-29 11:31
 * @Modified By:
 */
@Configuration
public class DeadConfig {
    @Bean
    FanoutExchange deadExchange(){
        return new FanoutExchange(RabbitConst.deadExchange,true,false);
    }
    @Bean
    Queue deadQueue(){
        return new Queue(RabbitConst.deadQueue,true);
    }
    @Bean
    Binding deadBinding(){
        return BindingBuilder.bind(deadQueue()).to(deadExchange());
    }
    @Bean
    DirectExchange directExchangeBindDead(){
        return new DirectExchange("testDirectEx",true,false);
    }
    @Bean
    Queue directQueueBindDead(){
        Map<String,Object> deadmap = new HashMap<>();
        // 绑定该队列到私信交换机
        deadmap.put("x-dead-letter-exchange",RabbitConst.deadExchange);
//        deadmap.put("x-dead-letter-routing-key",null);//由于deadExchange是fanout类型的所以不需要routing-key
        return new Queue("testDirectQue",true,false,false,deadmap);
    }

    @Bean
    Binding directBindDead(){
        return BindingBuilder.bind(directQueueBindDead()).to(directExchangeBindDead()).with("test");
    }
}
