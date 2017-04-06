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

public class KafkaDayBatchProducer {
    public static void main(String[] args) {
//        boolean isAsync = args.length == 0 || !args[0].trim().equalsIgnoreCase("sync");
    	 boolean isAsync =false;
//    	 DayBatchProducer producerThread = new DayBatchProducer(KafkaProperties.TOPIC, isAsync,"20170324");
//         producerThread.start();

        Consumer consumerThread = new Consumer(KafkaProperties.TOPIC);
        consumerThread.start();
//        

    }
}
