package com.qingting.platform.message;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SendCallback implements Callback {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SendCallback.class);
	
	@Override
	public void onCompletion(RecordMetadata metadata, Exception exception) {
		if(exception!=null){//失败
			LOGGER.debug("Message send fail . message = {}, ",exception.getMessage());
			exception.printStackTrace();
		}else{//成功
			LOGGER.debug("Message send success . partition = {}, offset = {}",metadata.partition() , metadata.offset());
		}
	}

}
