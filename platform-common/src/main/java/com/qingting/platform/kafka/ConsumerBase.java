package com.qingting.platform.kafka;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.qingting.platform.config.ConfigUtils;


public class ConsumerBase {
	
	public static Properties props;
	
	/**
	 * 注：kafka消费者不是线程安全的
	 */
	private KafkaConsumer<String, String> consumer;
	
	static{
		props = new Properties();
		
		//kafka地址
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, ConfigUtils.getProperty("bootstrap.servers"));
        
        //消费者的组id
        props.put(ConsumerConfig.GROUP_ID_CONFIG, ConfigUtils.getProperty("group.id"));
        
        //设置自动提交偏移量(offset),由auto.commit.interval.ms控制提交频率
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, ConfigUtils.getProperty("enable.auto.commit"));
        
        //设置使用最开始的offset偏移量为该group.id的最早。如果不设置，则会是latest即该topic最新一个消息的offset
        //如果采用latest，消费者只能得道其启动后，生产者生产的消息
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, ConfigUtils.getProperty("auto.offset.reset"));
        
        //偏移量(offset)提交频率
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, ConfigUtils.getProperty("auto.commit.interval.ms"));

        //从poll(拉)的回话处理时长
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, ConfigUtils.getProperty("session.timeout.ms"));
        
        //poll的数量限制
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, ConfigUtils.getProperty("max.poll.records"));
        
        //key
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ConfigUtils.getProperty("key.deserializer"));
        
        //value
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ConfigUtils.getProperty("value.deserializer"));
	}
	/**
	 * 主题注入，同时创建消费者
	 * @param topic
	 */
	public void setTopic(String topic) {
		this.consumer = new KafkaConsumer<String, String>(props);
		this.consumer.subscribe(Arrays.asList(topic));
	}

	public KafkaConsumer<String, String> getConsumer() {
		return consumer;
	}
	/**
	 * 消息拉取
	 * @param timeout
	 * @return ConsumerRecords<String,String>
	 */
	public ConsumerRecords<String, String> poll(Long timeout){
		return consumer.poll(timeout);
	}
	
}
