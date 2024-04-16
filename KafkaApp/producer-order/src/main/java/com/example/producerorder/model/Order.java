package com.example.producerorder.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {
    private String product;
    private Integer quantity;

    @Override
    public String toString() {
        return "Order{" +
                "product='" + product + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
