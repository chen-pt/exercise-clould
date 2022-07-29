package com.person.chenpt.listener;

import com.person.chenpt.constans.RabbitConst;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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
    public void process(String testMsg, Message message, Channel channel) throws IOException {
        System.out.println("DirectReceiver1消费者收到消息  : " + testMsg);
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            System.out.println("消费的主题消息来自："+message.getMessageProperties().getConsumerQueue());
            channel.basicAck(deliveryTag, true); //第二个参数，手动确认可以被批处理，当该参数为 true 时，则可以一次性确认 delivery_tag 小于等于传入值的所有消息
        } catch (Exception e) {
            System.out.println("=====================异常了========================");
            if (message.getMessageProperties().getRedelivered()) {
                System.out.println("================消息已重复处理失败,拒绝再次接收======================" + testMsg);
                // 拒绝消息，requeue=false 表示不再重新入队，如果配置了死信队列则进入死信队列、true会重新放回队列，所以需要自己根据业务逻辑判断什么时候使用拒绝
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            } else {
                System.out.println("====================消息即将再次返回队列处理=========================" + testMsg);
                // requeue为是否重新回到队列，true重新入队
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            }
        }
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
