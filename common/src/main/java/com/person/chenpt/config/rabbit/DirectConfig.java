package com.person.chenpt.config.rabbit;

import com.person.chenpt.constans.RabbitConst;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 操作rabbitmq消息队列步骤
 * 代码中配置、也可以采用注解方式配置、也可在rabbit控制台操作（最好在代码中配置）
 * 1：声明交换机
 * 2：声明队列
 * 3：创建绑定Key
 * 4:bind队列与交换机
 *
 *
 * direct 直连交换机  根据路由key进行分发  多个队列binding同一个交换机
 * 配置多台监听绑定到同一个直连交互的同一个队列
 * 会以轮询的方式对消息进行消费，而且不存在重复消费。
 *
 *
 * @Author: chenpt
 * @Description:
 * @Date: Created in 2022-07-28 11:08
 * @Modified By:
 */
@Configuration
public class DirectConfig {

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(RabbitConst.directExchange,true,false,null);
    }

    @Bean
    public Queue directQueue(){
        /**
         * durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
         * autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
         */
        return new Queue(RabbitConst.directQueue,true);
    }

    @Bean
    public Binding bindingDirectQueue(){
        return BindingBuilder.bind(directQueue()).to(directExchange()).with(RabbitConst.directRoutingKey);
    }

    @Bean
    public Queue directQueue2(){
        return new Queue(RabbitConst.directQueue2,true);
    }

    @Bean
    public Binding bindingDirectQueue2(){
        return BindingBuilder.bind(directQueue2()).to(directExchange()).with(RabbitConst.directRoutingKey);
    }


}
