package com.example.MVC_ObjectMapper.Entities;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "custom_order")
public class Order {

    @Id
    long orderId;
    @ManyToOne(cascade=CascadeType.ALL )
    Customer customer;
    @OneToMany(cascade=CascadeType.ALL)
    List<Product> products;
    @NotNull
    LocalDateTime orderDate;
    @NotNull
    String shippingAddress;
    @NotNull
    @Min(0)
    long totalPrice;
    @NotNull
    String status;

    public Order() {
    }

    public Order(long orderId, Customer customer, List<Product> products, LocalDateTime orderDate, String shippingAddress, long totalPrice, String status) {
        this.orderId = orderId;
        this.customer = customer;
        this.products = products;
        this.orderDate = orderDate;
        this.shippingAddress = shippingAddress;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId == order.orderId && totalPrice == order.totalPrice && Objects.equals(customer, order.customer) && Objects.equals(products, order.products) && Objects.equals(orderDate, order.orderDate) && Objects.equals(shippingAddress, order.shippingAddress) && Objects.equals(status, order.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, customer, products, orderDate, shippingAddress, totalPrice, status);
    }
}
