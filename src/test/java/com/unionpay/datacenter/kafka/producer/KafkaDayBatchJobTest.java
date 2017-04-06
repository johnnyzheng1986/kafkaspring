package com.unionpay.datacenter.kafka.producer;


import java.util.Properties;

import javax.annotation.Resource;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.unionpay.datacenter.kafka.config.SpringConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=SpringConfiguration.class)
public class KafkaDayBatchJobTest {
	
	private KafkaProducer<Integer, String> producer;
	@Resource
	private DayBatchProducer dayBatchProducer;

	@Test
	public void testDayBatchJob() {
		Properties props = new Properties();
		props.put("bootstrap.servers", KafkaProperties.KAFKA_SERVER_URL + ":" + KafkaProperties.KAFKA_SERVER_PORT);
		props.put("client.id", "DemoProducer");
		props.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		producer = new KafkaProducer<>(props);
		dayBatchProducer.setProducer(producer);
		dayBatchProducer.setTopic(KafkaProperties.TOPIC);
		dayBatchProducer.setIsAsync(false);
		dayBatchProducer.setDayend("20170314");
		dayBatchProducer.run();
	}
	
	@Test
	public void testConsumer() {
        Consumer consumerThread = new Consumer(KafkaProperties.TOPIC);
        consumerThread.start();
	}

}
