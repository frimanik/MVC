package com.example.Spring.MVC.json.view.Entities;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;


public class Order {

    @NotNull
    long id;
    @NotNull

    String product;
    @NotNull
    @Min(0)
    long price;
    String status;

    public Order(long id,String product, long price, String status) {
        this.id = id;
        this.product = product;
        this.price = price;
        this.status = status;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
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
        return id == order.id && price == order.price && Objects.equals(product, order.product) && Objects.equals(status, order.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product, price, status);
    }
}
