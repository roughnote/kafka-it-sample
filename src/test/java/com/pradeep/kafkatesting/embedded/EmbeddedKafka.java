package com.pradeep.kafkatesting.embedded;

import com.pradeep.kafkatesting.ExampleRepository;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.kafka.test.rule.EmbeddedKafkaRule;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

import static org.mockito.Mockito.timeout;

@RunWith(SpringRunner.class)
@ActiveProfiles("embedded")
@SpringBootTest
public class EmbeddedKafka {

    @ClassRule

    public static EmbeddedKafkaRule embeddedKafka =
            new EmbeddedKafkaRule(1, false, "sample-topic");


    @Autowired
    private KafkaTemplate<String, Object> template;

    @MockBean
    private ExampleRepository repository;

    @Value("${topic.name}")
    private String topicName;

    @Test
    public void sayHello() throws ExecutionException, InterruptedException {
        System.out.println(embeddedKafka.getEmbeddedKafka().getBrokersAsString());
        System.out.println("==============sayHelloEmbeddedKafka===============");
        ListenableFuture<SendResult<String, Object>> response = this.template.send(topicName, "1", "Hello from EmbeddedKafka");
        System.out.println(response.get().toString());
        Mockito.verify(repository, timeout(5000).times(1)).persist("Hello from EmbeddedKafka");

    }

    @Test
    public void sayHelloTwice() {
        System.out.println("==============sayHelloTwice===============");
        this.template.send(topicName, "2", "Hello again EmbeddedKafka");
        this.template.send(topicName, "3", "Hello again EmbeddedKafka");
        Mockito.verify(repository, timeout(5000).times(2)).persist("Hello again EmbeddedKafka");
    }
}
