package com.person.chenpt.config.rabbit;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 生产者
 * 消息确认
 * 可以在回调函数根据需求做对应的扩展或者业务数据处理
 * @Author: chenpt
 * @Description:
 * @Date: Created in 2022-07-28 16:00
 * @Modified By:
 */
@Configuration
public class RabbitProductConfirmConfig {

    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        //设置开启Mandatory,才能触发回调函数,无论消息推送结果怎么样都强制调用回调函数
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.println("ConfirmCallback:     "+"相关数据："+correlationData);
                System.out.println("ConfirmCallback:     "+"确认情况："+ack);
                System.out.println("ConfirmCallback:     "+"原因："+cause);
            }
        });

        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            @Override
            public void returnedMessage(ReturnedMessage returned) {
                System.out.println("ReturnCallback:     "+"消息："+returned.getMessage());
                System.out.println("ReturnCallback:     "+"回应码："+returned.getReplyCode());
                System.out.println("ReturnCallback:     "+"回应信息："+returned.getReplyText());
                System.out.println("ReturnCallback:     "+"交换机："+returned.getExchange());
                System.out.println("ReturnCallback:     "+"路由键："+returned.getRoutingKey());
            }
        });

        return rabbitTemplate;
    }


}
