package com.person.chenpt.listener;

import com.person.chenpt.constans.RabbitConst;
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



    @RabbitListener(queues = RabbitConst.directQueue)
    public void process(String testMsg){
        System.out.println("DirectReceiver1消费者收到消息  : " + testMsg);
    }

    @RabbitListener(queues = RabbitConst.directQueue2)
    public void process2(String testMsg){
        System.out.println("DirectReceiver2消费者收到消息  : " + testMsg);
    }

    @RabbitListener(queues = RabbitConst.fanoutQueue1)
    public void fanoutQueue1(String testMsg){
        System.out.println("fanoutReceiver1消费者收到消息  : " + testMsg);
    }

    @RabbitListener(queues = RabbitConst.fanoutQueue2)
    public void fanoutQueue2(String testMsg){
        System.out.println("fanoutReceiver2消费者收到消息  : " + testMsg);
    }

    @RabbitListener(queues = RabbitConst.topicQueue1)
    public void topicQueue1(String testMsg){
        System.out.println("topicReceiver1消费者收到消息  : " + testMsg);
    }

    @RabbitListener(queues = RabbitConst.topicQueue2)
    public void topicQueue2(String testMsg){
        System.out.println("topicReceiver2消费者收到消息  : " + testMsg);
    }





}
