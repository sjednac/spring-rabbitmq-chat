package com.mintbeans.chat.config;

import com.mintbeans.chat.AppSettings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Binding.DestinationType;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.util.Collections.emptyMap;

@Slf4j
@Configuration
public class RabbitBindingConfig {

	@Bean
	@Autowired
	public Exchange exchange(AppSettings settings, RabbitAdmin rabbitAdmin) {
		final Exchange exchange = new FanoutExchange(settings.getRabbit().getExchange(), true, false);

		log.info("Declaring exchange: {}", exchange.getName());
		rabbitAdmin.declareExchange(exchange);

		return exchange;
	}

	@Bean
	@Autowired
	public Queue queue(AppSettings settings, RabbitAdmin rabbitAdmin) {
		final Queue queue = new Queue(settings.getRabbit().getQueue(), true);

		log.info("Declaring queue: {}", queue.getName());
		rabbitAdmin.declareQueue(queue);

		return queue;
	}

	@Bean
	@Autowired
	public Binding binding(RabbitAdmin rabbitAdmin, Exchange exchange, Queue queue) {
		final Binding binding = new Binding(queue.getName(), DestinationType.QUEUE, exchange.getName(), "#", emptyMap());

		log.info("Declaring binding: {} -> {}", binding.getExchange(), binding.getDestination());
		rabbitAdmin.declareBinding(binding);

		return binding;
	}

}
