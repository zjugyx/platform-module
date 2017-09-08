package com.qingting.platform.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class ProducerInstance extends ProducerBase{
	/**
	 * kafka生产者是线程安全的
	 */
	private Producer<String, String> producer = null;
	
	
	public ProducerInstance() {
		super();
		this.producer=new KafkaProducer<String, String>(props);
	}

	/*public ProducerInstance(Producer<K, V> producer) {
		super();
		this.producer = producer;
	}*/

	public void send(String topic, Integer partition, String key, String value){
		ProducerRecord<String, String> record =new ProducerRecord<String,String>(topic,partition, key,value);
		producer.send(record);
	}
	
	public void close(){
		producer.close();
	}
	
}
