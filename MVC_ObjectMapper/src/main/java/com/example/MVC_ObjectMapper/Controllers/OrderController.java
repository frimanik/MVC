package com.example.MVC_ObjectMapper.Controllers;

import com.example.MVC_ObjectMapper.Entities.Order;
import com.example.MVC_ObjectMapper.Exceptions.OrderWasNotFoundException;
import com.example.MVC_ObjectMapper.Repositories.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;;import java.time.LocalDateTime;

@RestController
public class OrderController {

    private final OrderRepository orderRepository;

    private final ObjectMapper objectMapper;

    @Autowired
    public OrderController(OrderRepository orderRepository, ObjectMapper objectMapper) {
        this.orderRepository = orderRepository;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/getOrder/{id}")
    public String getOrder(@PathVariable  Long id) throws JsonProcessingException {
        return objectMapper.writeValueAsString(orderRepository.findById(id).orElseThrow(()-> new OrderWasNotFoundException("Order with this id does not exist")));
    }

    @PostMapping("/addOrder")
    public Order addOrder(@RequestBody String order) throws JsonProcessingException {
        Order savedOrder = objectMapper.readValue(order,Order.class);
        savedOrder.setOrderDate(LocalDateTime.now());
        return orderRepository.save(savedOrder);
    }

    @ExceptionHandler(OrderWasNotFoundException.class)
    public ResponseEntity<String> handleOrderNotFoundException(OrderWasNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
