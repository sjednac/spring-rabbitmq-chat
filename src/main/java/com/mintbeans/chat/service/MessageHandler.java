package com.mintbeans.chat.service;

import com.mintbeans.chat.model.Message;

public interface MessageHandler {
	void handleIncomingMessage(Message msg);
}
