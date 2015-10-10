package com.mintbeans.chat.service;

import com.mintbeans.chat.AppSettings;
import com.mintbeans.chat.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessagePublisher {

	private final RabbitTemplate rabbitTemplate;

	private final String exchange;

	@Autowired
	public MessagePublisher(AppSettings settings, RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
		this.exchange = settings.getRabbit().getExchange();
	}

	public void publish(Message msg) {
		log.debug("Publishing message: {}", msg);
		rabbitTemplate.convertAndSend(exchange, null, msg);
	}

}
