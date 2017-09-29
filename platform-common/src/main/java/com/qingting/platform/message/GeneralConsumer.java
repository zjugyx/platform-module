package com.qingting.platform.message;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.qingting.platform.kafka.ConsumerBase;

public class GeneralConsumer implements InitializingBean {
	private static final Logger LOGGER = LoggerFactory.getLogger(GeneralConsumer.class);
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
	private GeneralHandler generalHandler;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		 new Thread(new Runnable(){
				@Override
				public void run() {
					while (true) {
						ConsumerRecords<String, String> records = consumerBase.poll(timeout);
						for (ConsumerRecord<String, String> record : records){
							LOGGER.debug("consumer poll record: offset = {}, key = {}, value = {}", record.offset(), record.key(), record.value());
							try{
								generalHandler.parser(record.key(),record.value());
							}catch(Exception e){
								LOGGER.debug("message format error. please check the message of json. offset = {}, key = {}, value = {}", record.offset(), record.key(), record.value());
							}
			            }
			        }
				}
	        }).start();
	}

	public void setTimeout(Long timeout) {
		this.timeout = timeout;
	}

	public void setConsumerBase(ConsumerBase consumerBase) {
		this.consumerBase = consumerBase;
	}

	public void setGeneralHandler(GeneralHandler generalHandler) {
		this.generalHandler = generalHandler;
	}

}
