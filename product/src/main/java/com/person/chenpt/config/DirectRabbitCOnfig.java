package com.person.chenpt.config;


import com.person.chenpt.constant.MessageQueueConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: chenpt
 * @Description:
 * @Date: Created in 2021-11-09 15:50
 * @Modified By:
 */
@Configuration
public class DirectRabbitCOnfig {

    public static String directRoutingKey = "default.direct.key";

    @Bean
    public Queue TestDirectQueue(){
        /**
         *  durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
         *  exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
         *  autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
         *  return new Queue("TestDirectQueue",true,true,false);
         *  一般设置一下队列的持久化就好,其余两个就是默认false
         */
        return new Queue("TestDirectQueue",true);
    }

    @Bean
    public DirectExchange TestDirectExchange(){
        return new DirectExchange(MessageQueueConstants.DEFAULT_DIRECT_EXCHANGE,true,false);
    }

    @Bean
    public Binding bingDirect(){
        return BindingBuilder.bind(TestDirectQueue()).to(TestDirectExchange()).with(directRoutingKey);
    }


}
