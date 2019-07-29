package com.pradeep.kafkatesting;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.timeout;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class KafkaExampleIT {

    @Autowired
    private KafkaTemplate<String, Object> template;

    @MockBean
    private ExampleRepository repository;

    @Value("${topic.name}")
    private String topicName;

    @Test
    public void sayHello() {
        System.out.println("==============sayHello===============");
        this.template.send(topicName, "1", "Hello from test");
        Mockito.verify(repository, timeout(5000).times(1)).persist("Hello from test");

    }

    @Test
    public void sayHelloTwice() {
        System.out.println("==============sayHelloTwice===============");
        this.template.send(topicName, "2", "Hello again");
        this.template.send(topicName, "3", "Hello again");
        Mockito.verify(repository, timeout(5000).times(2)).persist("Hello again");
    }
}
