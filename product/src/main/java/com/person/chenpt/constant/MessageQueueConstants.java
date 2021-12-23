package com.person.chenpt.constant;

/**
 * rabbitmq 消息队列常量
 */
public class MessageQueueConstants {
	
	/**
	 * 默认即时消息交换机名称
	 */
	public static String DEFAULT_DIRECT_EXCHANGE = "default.direct.exchange";

	/**
	 * 默认延时功能消息交换机名称
	 */
	public static String DEFAULT_DELAYED_DIRECT_EXCHANGE = "default.delayed.exchange";
	/**
	 * 错误队列
	 */
	public static final String QUEUE_ERROR = "queue.error";
	/**
	 * 消费队列
	 */
	public static final String QUEUE_CONSUME = "queue.consume";
}