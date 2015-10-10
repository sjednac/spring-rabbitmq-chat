package com.mintbeans.chat.web;

import com.mintbeans.chat.model.Message;
import com.mintbeans.chat.service.MessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/messages")
public class MessageController {

	@Autowired
	private MessagePublisher publisher;

	@RequestMapping(method = POST)
	public String post(@RequestBody String content) {
		final Message msg = Message.textMessage(content);
		publisher.publish(msg);
		return msg.getId();
	}

}
