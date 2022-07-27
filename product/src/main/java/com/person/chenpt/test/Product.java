package com.person.chenpt.test;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: chenpt
 * @Description:
 * @Date: Created in 2022-07-22 16:52
 * @Modified By:
 */
public class Product {

    private static String directQueueName = "firstDirectQueue";
    private static String directQueueName_key = "first_key";
    private static String directExchange = "ideaExchange";

    public static void main(String[] args) {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.3.85");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");
        Connection connection = null;
        try {
            connection = factory.newConnection();

            Channel channel = connection.createChannel();
            channel.exchangeDeclare(directExchange, BuiltinExchangeType.DIRECT);
            channel.queueDeclare(directQueueName,false , false , false , null);
            channel.queueBind(directQueueName,directExchange,directQueueName_key);
            channel.basicPublish(directExchange,directQueueName_key,null,"你好sad".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println();
    }


}
