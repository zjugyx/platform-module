package com.qingting.platform.message;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.qingting.platform.kafka.ConsumerBase;

public class MessageConsumer implements InitializingBean {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MessageConsumer.class);
	/**
	 * 拉取超时时间
	 */
	private Long timeout;
	/**
	 * 消费者基础类
	 */
	private ConsumerBase consumerBase;
	/**
	 * 消息处理类
	 */
	private MessageHandler messageHandler;
	
	/**
	 * 该类实体创建后，独立线程消费
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		while (true) {
			ConsumerRecords<String, String> records = consumerBase.poll(timeout);
			for (ConsumerRecord<String, String> record : records){
				LOGGER.debug("consumer data: offset = {}, key = {}, value = {}", record.offset(), record.key(), record.value());
				
				//BaseBean<String> bean = JSON.parseObject("",new TypeReference<BaseBean<String>>() {});   
				messageHandler.parser(record.key(),JSON.parseObject(record.value(),MessageServer.class));
            }
        }
	}

	public void setTimeout(Long timeout) {
		this.timeout = timeout;
	}

	public void setConsumerBase(ConsumerBase consumerBase) {
		this.consumerBase = consumerBase;
	}

	public void setMessageHandler(MessageHandler messageHandler) {
		this.messageHandler = messageHandler;
	}

}
