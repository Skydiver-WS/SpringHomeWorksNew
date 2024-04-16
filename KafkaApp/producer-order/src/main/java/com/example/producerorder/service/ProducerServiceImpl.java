package com.example.producerorder.service;

import com.example.producerorder.model.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProducerServiceImpl implements ProducerService{
    private final KafkaTemplate<String, Object> kafkaTemplate;
    @Value("${spring.kafka.producer.topic.create-order}")
    private String topic;
    @Override
    public void sendMessage(Order order) {
        log.info("Sending message");
        kafkaTemplate.send(topic, UUID.randomUUID().toString(), order);
        log.info("Message sent {}", order.toString());
    }
}
