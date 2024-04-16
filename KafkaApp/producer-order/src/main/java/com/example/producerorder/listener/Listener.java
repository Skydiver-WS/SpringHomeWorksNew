package com.example.producerorder.listener;

import com.example.producerorder.model.Order;
import com.example.producerorder.model.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class Listener {
    @KafkaListener(topics = "${spring.kafka.producer.topic.create-order-status}",
            groupId = "${spring.kafka.producer.topic.kafkaMessageGroupId}",
            containerFactory = "kafkaListenerContainerFactory")
    @Retryable(maxAttempts = 2, backoff = @Backoff(delay = 1000))
    public void listen(
            @Payload Status status,
            @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) UUID key,
            @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.RECEIVED_TIMESTAMP) Long timestamp
    ) {

        log.info(status.toString());
        log.info("Key: {}; Partition: {}; Topic: {}, Timestamp: {}", key, partition, topic, timestamp);

    }


}
