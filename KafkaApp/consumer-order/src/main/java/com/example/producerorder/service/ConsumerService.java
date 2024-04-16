package com.example.producerorder.service;

import com.example.producerorder.model.Status;

public interface ConsumerService {
    void sendMessage(Status status);
}
