package com.mintbeans.chat.service;

import com.mintbeans.chat.AppSettings;
import com.mintbeans.chat.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessagePublisher {

	private final AmqpTemplate amqpTemplate;

	private final String exchange;

	@Autowired
	public MessagePublisher(AppSettings settings, AmqpTemplate amqpTemplate) {
		this.amqpTemplate = amqpTemplate;
		this.exchange = settings.getRabbit().getExchange();
	}

	public void publish(Message msg) {
		log.debug("Publishing message: {}", msg);
		amqpTemplate.convertAndSend(exchange, null, msg);
	}

}
