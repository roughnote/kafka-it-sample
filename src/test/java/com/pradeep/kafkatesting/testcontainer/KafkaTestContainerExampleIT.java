package com.pradeep.kafkatesting.testcontainer;

import com.pradeep.kafkatesting.ExampleRepository;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

import static org.mockito.Mockito.timeout;


public class KafkaTestContainerExampleIT extends KafkaTestContainerBase {

    @Autowired
    private KafkaTemplate<String, Object> template;

    @MockBean
    private ExampleRepository repository;

    @Value("${topic.name}")
    private String topicName;

    @Test
    public void sayHello() throws ExecutionException, InterruptedException {
        System.out.println("==============sayHelloTestContainer===============");
        ListenableFuture<SendResult<String, Object>> response = this.template.send(topicName, "1", "Hello from TestContainer");
        System.out.println(response.get().toString());
        Mockito.verify(repository, timeout(5000).times(1)).persist("Hello from TestContainer");

    }

    @Test
    public void sayHelloTwice() {
        System.out.println("==============sayHelloTwice===============");
        this.template.send(topicName, "2", "Hello again");
        this.template.send(topicName, "3", "Hello again");
        Mockito.verify(repository, timeout(5000).times(2)).persist("Hello again");
    }
}

