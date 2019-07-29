package com.pradeep.kafkatesting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ExampleRepository {

    private static final Logger logger = LoggerFactory.getLogger(ExampleRepository.class);

    public void persist(String message) {
        logger.info("Logger [String] received {}", message);
    }
}
