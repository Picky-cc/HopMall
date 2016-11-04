package com.suidifu.hathaway.mq;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.support.CorrelationData;

public class RemoteServiceAccessor {

	private String requestQueueName;
	private String replyQueueName;
	private String routingKey;
	private RabbitTemplate rabbitTemplate;
	private String exchange;
	private SimpleMessageListenerContainer container;

	public void injectRabbitTemplate(ConnectionFactory connectionFactory) {
		this.rabbitTemplate = new RabbitTemplate(connectionFactory);
		// The routing key is set to the name of the queue by the broker for the
		// default exchange.
		rabbitTemplate.setRoutingKey(this.routingKey);
		// Where we will synchronously receive messages from
		rabbitTemplate.setQueue(this.requestQueueName);
		// template.setMessageConverter(new JsonMessageConverter());
		rabbitTemplate.setReplyAddress(replyQueueName);

		container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(replyQueueName);
		container.setMessageListener(rabbitTemplate);
		container.start();

	}

	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	public void setRoutingKey(String routingKey) {
		this.routingKey = routingKey;
	}

	public String getRequestQueueName() {
		return requestQueueName;
	}

	public void setRequestQueueName(String requestQueueName) {
		this.requestQueueName = requestQueueName;
	}

	public String getReplyQueueName() {
		return replyQueueName;
	}

	public void setReplyQueueName(String replyQueueName) {
		this.replyQueueName = replyQueueName;
	}

	public String getRoutingKey() {
		return routingKey;
	}

	public void send(String s) {

		this.rabbitTemplate.convertAndSend(s);
	}

	public void send(String exchange, String routingKey, Object msg) {

		this.rabbitTemplate.convertAndSend(exchange, routingKey, msg);
	}

	public Object sendAndReceive(Object msg) {
		return this.rabbitTemplate.convertSendAndReceive(this.exchange,
				this.routingKey, msg);
	}
	public Object send(Object msg,CorrelationData correlationData) {
		this.rabbitTemplate.convertAndSend(this.exchange,
				this.routingKey, msg,correlationData);
		return correlationData.getId();
	}
}
