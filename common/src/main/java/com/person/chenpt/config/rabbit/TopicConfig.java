package com.person.chenpt.config.rabbit;

import com.person.chenpt.constans.RabbitConst;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 主题模式
 * 主题交换机，对路由键进行模式匹配后进行投递，
 * 符号 # 表示一个或多个词，
 * 符号 * 表示一个词。
 * 因此“abc.#”能够匹配到“abc.def.ghi”，
 * 但是“abc.*” 只会匹配到“abc.def”
 * @Author: chenpt
 * @Description:
 * @Date: Created in 2022-07-28 15:28
 * @Modified By:
 */
@Configuration
public class TopicConfig {
    @Bean
    TopicExchange topicExchange(){
        return new TopicExchange(RabbitConst.topicExchange,true,false);
    }
    @Bean
    Queue topicQueue1(){
        return new Queue(RabbitConst.topicQueue1,true);
    }
    @Bean
    Queue topicQueue2(){
        return new Queue(RabbitConst.topicQueue2,true);
    }
    @Bean
    Binding bindingTopicQueue1(){
        return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with(RabbitConst.topicRoutingKey1);
    }
    @Bean
    Binding bindingTopicQueue2(){
        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with(RabbitConst.topicRoutingKey2);
    }

}
