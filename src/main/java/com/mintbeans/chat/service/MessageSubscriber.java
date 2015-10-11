package com.mintbeans.chat.service;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

import com.mintbeans.chat.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageSubscriber {

	private final AtomicInteger counter = new AtomicInteger(0);

	@Autowired(required = false)
	private Collection<MessageHandler> messageHandlers;

	@RabbitListener(queues = "#{appSettings.rabbit.queue}")
	public void onMessage(Message message) {
		final int ordinal = counter.incrementAndGet();
		log.debug("Message {} received: {}", ordinal, message);
		if (messageHandlers != null) {
			messageHandlers.forEach(h -> h.handleIncomingMessage(message));
		}
	}

}
