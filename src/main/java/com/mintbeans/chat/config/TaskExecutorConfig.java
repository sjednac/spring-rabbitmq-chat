package com.mintbeans.chat.config;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.mintbeans.chat.AppSettings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Slf4j
@Configuration
public class TaskExecutorConfig {

	@Target({FIELD, PARAMETER, METHOD})
	@Retention(RUNTIME)
	@Qualifier
	public @interface RabbitConnectionExecutor {}

	@Target({FIELD, PARAMETER, METHOD})
	@Retention(RUNTIME)
	@Qualifier
	public @interface RabbitListenerExecutor {}

	/**
	 * "The executor’s thread pool should be unbounded, or set appropriately for the expected utilization (usually, at least one thread per connection). If multiple channels are created on each connection then the pool size will affect the concurrency, so a variable (or simple cached) thread pool executor would be most suitable."
	 *
	 * Reference:
	 * http://docs.spring.io/spring-amqp/reference/htmlsingle/#connections
	 */
	@Bean
	@Autowired
	@RabbitConnectionExecutor
	public TaskExecutor rabbitConnectionExecutor(AppSettings settings) {
		final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(settings.getRabbit().getMaxConnections());
		executor.setMaxPoolSize(settings.getRabbit().getMaxConnections());
		executor.setThreadNamePrefix("RabbitConnection");
		executor.afterPropertiesSet();
		return executor;
	}

	/**
	 * Listeners would use a SimpleAsyncTaskExecutor by default (creates a new thread for each task).
	 *
	 * Reference:
	 * http://docs.spring.io/spring-amqp/reference/htmlsingle/#_threading_and_asynchronous_consumers
	 */
	@Bean
	@Autowired
	@RabbitListenerExecutor
	public TaskExecutor rabbitListenerExecutor(AppSettings settings) {
		final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(settings.getRabbit().getMaxConcurrentConsumers());
		executor.setMaxPoolSize(settings.getRabbit().getMaxConcurrentConsumers());
		executor.setThreadNamePrefix("RabbitListener");
		executor.afterPropertiesSet();
		return executor;
	}

}
