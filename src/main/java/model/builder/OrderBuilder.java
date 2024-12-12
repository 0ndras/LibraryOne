package model.builder;

import model.Order;

import java.time.LocalDate;

public class OrderBuilder {
    private Order order;

    public OrderBuilder() {
        order = new Order();
    }

    public OrderBuilder setId(Long id) {
        order.setId(id);
        return this;
    }

    public OrderBuilder setEmployeeUsername(String employeeUsername) {
        order.setEmployeeUsername(employeeUsername);
        return this;
    }

    public OrderBuilder setBookTitle(String bookTitle) {
        order.setBookTitle(bookTitle);
        return this;
    }

    public OrderBuilder setSaleDate(LocalDate saleDate) {
        order.setSaleDate(saleDate);
        return this;
    }

    public Order build() {
        return order;
    }
}
