package com.person.chenpt.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: chenpt
 * @Description:
 * @Date: Created in 2021-11-09 16:55
 * @Modified By:
 */
@Component
public class Consumer {

    @RabbitListener(queues = "TestDirectQueue")
    public void process(Map testMsg){
        System.out.println("DirectReceiver消费者收到消息  : " + testMsg.toString());
    }


}
