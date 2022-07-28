package com.person.chenpt.config.rabbit;

import com.person.chenpt.constans.RabbitConst;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * 扇型交换机
 * 不需要路由键  多个队列binding到交换机都能收到消息
 * 与多个队列采用同一个路由键binding到direct交换机效果相同
 *
 *
 * @Author: chenpt
 * @Description:
 * @Date: Created in 2022-07-28 14:54
 * @Modified By:
 */
@Configuration
public class FanoutConfig {

    @Bean
    FanoutExchange fanoutExchange(){
        return new FanoutExchange(RabbitConst.fanoutExchange,true,false);
    }

    @Bean
    Queue fanoutQueue1(){
        return new Queue(RabbitConst.fanoutQueue1,true);
    }

    @Bean
    Queue fanoutQueue2(){
        return new Queue(RabbitConst.fanoutQueue2,true);
    }

    @Bean
    Binding fanoutBinding1(){
        return BindingBuilder.bind(fanoutQueue1()).to(fanoutExchange());
    }

    @Bean
    Binding fanoutBinding2(){
        return BindingBuilder.bind(fanoutQueue2()).to(fanoutExchange());
    }

}
