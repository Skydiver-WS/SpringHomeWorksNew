package com.example.producerorder.service;

import com.example.producerorder.model.Order;

public interface ProducerService {
    void sendMessage(Order order);
}
