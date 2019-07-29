package com.pradeep.kafkatesting;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KafkaListener {

    @Autowired
    private ExampleRepository repository;



    @org.springframework.kafka.annotation.KafkaListener(topics = "sample-topic")
    public void listen(ConsumerRecord<String, String> record) {

        repository.persist(record.value());

    }

}
