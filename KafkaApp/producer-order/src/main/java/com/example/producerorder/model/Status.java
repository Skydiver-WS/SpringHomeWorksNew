package com.example.producerorder.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Status {
    private String status;
    private Date date;

    @Override
    public String toString() {
        return "Status{" +
                "status='" + status + '\'' +
                ", date=" + date +
                '}';
    }
}
