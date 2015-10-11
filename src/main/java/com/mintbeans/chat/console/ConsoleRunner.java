package com.mintbeans.chat.console;

import java.util.Scanner;

import com.mintbeans.chat.model.Message;
import com.mintbeans.chat.service.MessageHandler;
import com.mintbeans.chat.service.MessagePublisher;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("cli")
public class ConsoleRunner implements CommandLineRunner, MessageHandler {

	private final MessagePublisher publisher;

	@Autowired
	public ConsoleRunner(MessagePublisher publisher) {
		this.publisher = publisher;
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("### RabbitMQ Chat ###");
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println("> ");
			String line = scanner.nextLine();
			publisher.publish(Message.textMessage(line));
		}
	}

	@Override
	public void handleIncomingMessage(@NonNull Message msg) {
		System.out.println(msg.getContent());
	}

}
