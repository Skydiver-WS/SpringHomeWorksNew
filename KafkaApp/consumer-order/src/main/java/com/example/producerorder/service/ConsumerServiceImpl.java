package com.example.producerorder.service;

import com.example.producerorder.configuration.StatusEnum;
import com.example.producerorder.model.Order;
import com.example.producerorder.model.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConsumerServiceImpl implements ConsumerService {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    @Value("${spring.kafka.producer.topic.create-order-status}")
    private String topic;
    @Override
    public void sendMessage(Status status) {
        log.info("Sending message");
        kafkaTemplate.send(topic, UUID.randomUUID().toString(), status);
        log.info("Message sent {}", status.toString());
    }
}
