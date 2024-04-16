package com.example.producerorder.configuration;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum StatusEnum {
    CREATE("Order create"),
    ERROR("Error create order");
    private final String value;
}
