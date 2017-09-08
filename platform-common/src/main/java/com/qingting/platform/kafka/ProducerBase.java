package com.qingting.platform.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qingting.platform.config.ConfigUtils;

public abstract class ProducerBase {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProducerBase.class);
	
	public static Properties props =null;
	
	static{
		props = new Properties();
		
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, ConfigUtils.getProperty("bootstrap.servers"));
		
		//The "all" setting we have specified will result in blocking on the full commit of the record, the slowest but most durable setting.
		//“所有”设置将导致记录的完整提交阻塞，最慢的，但最持久的设置。
        props.put(ProducerConfig.ACKS_CONFIG, ConfigUtils.getProperty("acks"));
        
        //如果请求失败，生产者也会自动重试，即使设置成０ the producer can automatically retry.
        props.put(ProducerConfig.RETRIES_CONFIG, ConfigUtils.getProperty("retries"));

        //The producer maintains buffers of unsent records for each partition. 
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, ConfigUtils.getProperty("batch.size"));
        
        //默认立即发送，这里这是延时毫秒数
        props.put(ProducerConfig.LINGER_MS_CONFIG, ConfigUtils.getProperty("linger.ms"));
        
        //生产者缓冲大小，当缓冲区耗尽后，额外的发送调用将被阻塞。时间超过max.block.ms将抛出TimeoutException
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, ConfigUtils.getProperty("buffer.memory"));
        
        //key
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, ConfigUtils.getProperty("key.serializer"));
        
        //value
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ConfigUtils.getProperty("value.serializer"));
	}
}
