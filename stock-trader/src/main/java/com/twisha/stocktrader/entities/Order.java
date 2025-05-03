package com.twisha.stocktrader.entities;

import java.time.LocalDateTime;

public class Order {
    private final String orderId;
    private final String userId;
    private final OrderType orderType;
    private final String stockSymbol;
    private final int quantity;
    private final double price;
    private final LocalDateTime timestamp;
    private OrderStatus status;

    public Order(String orderId, String userId, OrderType orderType, String stockSymbol, int quantity, double price, LocalDateTime timestamp) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderType = orderType;
        this.stockSymbol = stockSymbol;
        this.quantity = quantity;
        this.price = price;
        this.timestamp = timestamp;
        this.status = OrderStatus.ACCEPTED;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getUserId() {
        return userId;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
