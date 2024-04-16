package com.example.producerorder.controller;

import com.example.producerorder.model.Order;
import com.example.producerorder.service.ProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/producer")
@Slf4j
@RequiredArgsConstructor
public class OrderController {
    private final ProducerService producerService;
    @PostMapping("/send")
    public ResponseEntity<Void> sendOrder(@RequestBody Order order) {
      log.info("Calling sendOrder controller");
      producerService.sendMessage(order);
      return ResponseEntity.noContent().build();
    }
}
