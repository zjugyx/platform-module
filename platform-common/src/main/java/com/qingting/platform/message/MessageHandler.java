package com.qingting.platform.message;

public interface MessageHandler {
	<T> void parser(String messageKey,MessageServer<T> messageServer);
}
