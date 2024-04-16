package com.example.producerorder.listener;

import com.example.producerorder.configuration.StatusEnum;
import com.example.producerorder.model.Order;
import com.example.producerorder.model.Status;
import com.example.producerorder.service.ConsumerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class Listener {
    private final ConsumerService consumerService;

    @KafkaListener(topics = "${spring.kafka.producer.topic.create-order}",
            groupId = "${spring.kafka.producer.topic.kafkaMessageGroupId}",
            containerFactory = "kafkaListenerContainerFactory")

    @Retryable(maxAttempts = 2, backoff = @Backoff(delay = 1000))
    public void listen(
                       @Payload Order order,
                       @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) UUID key,
                       @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
                       @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                       @Header(KafkaHeaders.RECEIVED_TIMESTAMP) Long timestamp
    ) {
        if(order.getProduct() != null || order.getQuantity() != null){
            log.info(order.toString());
            log.info("Key: {}; Partition: {}; Topic: {}, Timestamp: {}", key, partition, topic, timestamp);
            consumerService.sendMessage(Status.builder()
                    .status(StatusEnum.CREATE.name())
                    .date(new Date())
                    .build());
        } else {
            log.error("Order is null. Retry get message.");
            consumerService.sendMessage(Status.builder()
                    .status(StatusEnum.ERROR.name())
                    .date(new Date())
                    .build());
            throw new NullPointerException("Order is null");
        }
    }


}
