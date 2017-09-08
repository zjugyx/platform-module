package com.qingting.platform.kafka;

import java.util.Arrays;

import org.apache.kafka.clients.consumer.KafkaConsumer;

public class ConsumerInstance extends ConsumerBase{
	/**
	 * 注：kafka消费者不是线程安全的
	 */
	private KafkaConsumer<String, String> consumer =null;

	public ConsumerInstance(String topic) {
		super();
		this.consumer = new KafkaConsumer<String, String>(props);
		this.consumer.subscribe(Arrays.asList(topic));
	}
	
	
}
