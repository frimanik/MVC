package com.example.MVC_ObjectMapper;


import com.example.MVC_ObjectMapper.Controllers.OrderController;
import com.example.MVC_ObjectMapper.Entities.Order;
import com.example.MVC_ObjectMapper.Exceptions.OrderWasNotFoundException;
import com.example.MVC_ObjectMapper.Repositories.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class OrderControllerTests {

	private OrderRepository orderRepository;
	private ObjectMapper objectMapper;
	private OrderController orderController;

	@BeforeEach
	void setUp() {
		orderRepository = mock(OrderRepository.class);
		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		orderController = new OrderController(orderRepository, objectMapper);
	}

	@Test
	void testGetOrder_OrderExists() throws JsonProcessingException {

		String orderJson = TestJson.json;
		Order order = objectMapper.readValue(orderJson, Order.class);
		when(orderRepository.existsById(order.getOrderId())).thenReturn(true);
		when(orderRepository.findById(order.getOrderId())).thenReturn(Optional.of(order));

		Order result = objectMapper.readValue(orderController.getOrder(order.getOrderId()),Order.class);

		assert(result).equals(order);
	}

	@Test
	void testGetOrder_OrderNotFound() {

		long orderId = 1;
		when(orderRepository.existsById(orderId)).thenReturn(false);

		assertThatThrownBy(() -> orderController.getOrder(orderId))
				.isInstanceOf(OrderWasNotFoundException.class)
				.hasMessage("Order with this id does not exist");
	}

	@Test
	void testAddOrder() throws JsonProcessingException {

		String orderJson = TestJson.json;
		Order order = objectMapper.readValue(orderJson,Order.class);
		when(orderRepository.save(any())).thenReturn(order);

		Order savedOrder = orderController.addOrder(orderJson);

		assert(savedOrder.equals(order));
	}
}
