package com.mintbeans.chat.config;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mintbeans.chat.AppSettings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Slf4j
@Configuration
public class JacksonConfig {

	@Bean
	@Autowired
	public Jackson2ObjectMapperBuilder objectMapperBuilder(AppSettings settings) {
		final Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
		return builder.indentOutput(settings.isIndentJson())
					  .modules(new JavaTimeModule());
	}

}
