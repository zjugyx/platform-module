package com.qingting.platform.message;

import com.alibaba.fastjson.JSON;
import com.qingting.platform.kafka.ProducerBase;

public class MessageProducer {
	
	private ProducerBase producerBase;
	
	private SendCallback sendCallback;
	/**
	 * 异步发送,不判断成功与否
	 * @param <T>
	 * @param topic
	 * @param partition
	 * @param messageServer
	 */
	public <T> void send(String topic, Integer partition,MessageServer<T> messageServer){
		producerBase.send(topic, partition, messageServer.getType(), JSON.toJSONString(messageServer));
	}
	/**
	 * 异步发送，并回调处理
	 * @param <T>
	 * @param topic
	 * @param partition
	 * @param messageServer void
	 */
	public <T> void sendAndAck(String topic, Integer partition,MessageServer<T> messageServer){
		producerBase.send(topic, partition, 
				messageServer.getType(), 
				JSON.toJSONString(messageServer),
				sendCallback
				);
	}
}
