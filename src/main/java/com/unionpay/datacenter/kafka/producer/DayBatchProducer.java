/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.unionpay.datacenter.kafka.producer;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;
import com.unionpay.datacenter.kafka.dao.DayBatchDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.annotation.Resource;

@Component
public class DayBatchProducer extends Thread {
	private KafkaProducer<Integer, String> producer;
	private String topic;
	private Boolean isAsync;
	private String dayend;

	@Resource
	private DayBatchDao dayBatchDao;

//	public DayBatchProducer(String topic, Boolean isAsync,String dayend) {
//		Properties props = new Properties();
//		props.put("bootstrap.servers", KafkaProperties.KAFKA_SERVER_URL + ":" + KafkaProperties.KAFKA_SERVER_PORT);
//		props.put("client.id", "DemoProducer");
//		props.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
//		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//		producer = new KafkaProducer<>(props);
//		this.topic = topic;
//		this.isAsync = isAsync;
//		this.dayend= dayend;
//		
//	}

	public void run() {
		// 从数据库获取每日的批量任务列表
		int messageNo = 1;
		List<Map<String, Object>> dayBatchList = new ArrayList<>();
		dayBatchList = dayBatchDao.queryDayBatchJob(dayend);
		String dayBathJSON = JSON.toJSONString(dayBatchList);
		long startTime = System.currentTimeMillis();
		try {
			producer.send(new ProducerRecord<>(topic, messageNo, dayBathJSON)).get();
			System.out.println("Sent message: (" + messageNo + ", " + dayBathJSON + ")");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		++messageNo;
	}

	public KafkaProducer<Integer, String> getProducer() {
		return producer;
	}

	public void setProducer(KafkaProducer producer) {
		this.producer = producer;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public Boolean getIsAsync() {
		return isAsync;
	}

	public void setIsAsync(Boolean isAsync) {
		this.isAsync = isAsync;
	}

	public String getDayend() {
		return dayend;
	}

	public void setDayend(String dayend) {
		this.dayend = dayend;
	}
}

class DayBatchCallBack implements Callback {

	private final long startTime;
	private final int key;
	private final String message;

	public DayBatchCallBack(long startTime, int key, String message) {
		this.startTime = startTime;
		this.key = key;
		this.message = message;
	}

	/**
	 * A callback method the user can implement to provide asynchronous handling
	 * of request completion. This method will be called when the record sent to
	 * the server has been acknowledged. Exactly one of the arguments will be
	 * non-null.
	 *
	 * @param metadata
	 *            The metadata for the record that was sent (i.e. the partition
	 *            and offset). Null if an error occurred.
	 * @param exception
	 *            The exception thrown during processing of this record. Null if
	 *            no error occurred.
	 */
	public void onCompletion(RecordMetadata metadata, Exception exception) {
		long elapsedTime = System.currentTimeMillis() - startTime;
		if (metadata != null) {
			System.out.println("message(" + key + ", " + message + ") sent to partition(" + metadata.partition() + "), "
					+ "offset(" + metadata.offset() + ") in " + elapsedTime + " ms");
		} else {
			exception.printStackTrace();
		}
	}
}
