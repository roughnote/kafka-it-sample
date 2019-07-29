package com.pradeep.kafkatesting.testcontainer;


import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.KafkaContainer;

@RunWith(SpringRunner.class)
@ContextConfiguration(initializers = {KafkaTestContainerBase.KafkaTestContainerInitializer.class})
@ActiveProfiles("test-containers")
@SpringBootTest
public class KafkaTestContainerBase {
    @ClassRule
    public static KafkaContainer kafka = new KafkaContainer();

    static class KafkaTestContainerInitializer implements
        ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext context) {
            ConfigurableEnvironment environment = context.getEnvironment();

            TestPropertyValues.of(
                    "spring.kafka.bootstrap-servers=" + kafka.getBootstrapServers()
            ).applyTo(context.getEnvironment());

            MutablePropertySources propertySources = environment.getPropertySources();

            System.out.println(propertySources);

        }
    }
}
