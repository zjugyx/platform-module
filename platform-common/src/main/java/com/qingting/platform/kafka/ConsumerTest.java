package com.qingting.platform.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

public class ConsumerTest {

	public static void main(String[] args) {
		ConsumerInstance consumer = new ConsumerInstance("customer");
		ConsumerRecords<String, String> records = consumer.getConsumer().poll(100);
		for (ConsumerRecord<String, String> record : records) {
			System.out.printf("offset = %d, key = %s, value = %s", record.offset(), record.key(), record.value()+"\n");
		}
	}

}
