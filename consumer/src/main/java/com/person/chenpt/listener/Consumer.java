package com.person.chenpt.listener;

import com.person.chenpt.constans.RabbitConst;
import com.rabbitmq.client.Channel;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author: chenpt
 * @Description:
 * @Date: Created in 2021-11-09 16:55
 * @Modified By:
 */
@Component
public class Consumer {
    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

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

    @RabbitListener(queues = RabbitConst.deadQueue)
    public void deadQueue(String testMsg, Message message, Channel channel) throws IOException {
        System.out.println("deadEx消费者收到消息  : " + testMsg);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
    }

    @RabbitListener(queues = "testDirectQue")
    public void testDirectQue(String testMsg, Message message, Channel channel) throws IOException {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
//            int err = 1/0;//模拟异常
            logger.info("testDirectQue消费者收到消息  : {},消费的主题消息来自  : {}",testMsg,message.getMessageProperties().getConsumerQueue());
            /**
             * 第二个参数，手动确认可以被批处理，当该参数为 true 时，则可以一次性确认 delivery_tag 小于等于传入值的所有消息
             */
            channel.basicAck(deliveryTag, true);
        } catch (Exception e) {
            logger.info("=====================消费者异常========================");
            /**
             * message.getMessageProperties().getRedelivered()
             * false 表示第一次进队列
             * true  表示重入队列
             */
            if (message.getMessageProperties().getRedelivered()) {
                logger.info("================消息已重复处理失败,拒绝再次接收======================" + testMsg);
                /**
                 * 拒绝消息，requeue=false 表示不再重新入队，如果配置了死信队列则进入死信队列、
                 * true会重新放回队列，所以需要自己根据业务逻辑判断什么时候使用拒绝
                 */
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            } else {
                logger.info("====================消息即将再次返回队列处理=========================" + testMsg);
                /**
                 * requeue为是否重新回到队列，true重新入队
                 */
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            }
        }
    }





}
