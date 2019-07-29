package com.pradeep.kafkatesting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloKafkaController {

    private static final Logger logger =
            LoggerFactory.getLogger(HelloKafkaController.class);

    private final KafkaTemplate<String, Object> template;
    private final String topicName;

    public HelloKafkaController(
            final KafkaTemplate<String, Object> template,
            @Value("${topic.name}") final String topicName) {
        this.template = template;
        this.topicName = topicName;
    }

    @GetMapping("/hello")
    public String hello() {
        this.template.send(topicName, "1", "Hello 1");
        return "Message Sent";
    }
}
