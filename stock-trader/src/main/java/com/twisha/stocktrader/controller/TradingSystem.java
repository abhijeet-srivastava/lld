package com.twisha.stocktrader.controller;

import com.twisha.stocktrader.entities.Order;
import com.twisha.stocktrader.entities.OrderType;
import com.twisha.stocktrader.entities.Trade;
import com.twisha.stocktrader.entities.User;
import com.twisha.stocktrader.service.StockOrderBook;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class TradingSystem {

    private final ConcurrentHashMap<String, User> users;
    private final ConcurrentHashMap<String, Order> orders;
    private final ConcurrentHashMap<String, StockOrderBook> orderBooks;
    private final ConcurrentHashMap<String, Trade> trades;

    private final AtomicInteger orderIdGenerator;

    public TradingSystem() {
        this.users = new ConcurrentHashMap<>();
        this.orders = new ConcurrentHashMap<>();
        this.trades = new ConcurrentHashMap<>();
        this.orderBooks = new ConcurrentHashMap<>();

        this.orderIdGenerator = new AtomicInteger(1);
    }

    private void intializeDummyUser() {
        users.put("U1", new User("U1", "dummy_user1", "+(124)-523234", "abc@xyz.com"));
        users.put("U2", new User("U2", "dummy_user2", "+(124)-523236", "pqr@xyz.com"));
    }

    public String addOrder(String userId, String stockSymbol, OrderType orderType,  int quantity, double price) throws IllegalArgumentException {
        if(!this.users.contains(userId)) {
            throw new IllegalArgumentException("Invalid user");
        } else if(quantity <= 0 || price <= 0.0d) {
            throw new IllegalArgumentException("Invalid order");
        }
        int orderId = this.orderIdGenerator.getAndIncrement();
        Order order = new Order("O" + orderId, userId, orderType, stockSymbol, quantity, price, LocalDateTime.now());
        orders.put(order.getOrderId(), order);
        orderBooks.computeIfAbsent(stockSymbol, StockOrderBook::new).addOrder(order);

        // Match order
        List<Trade> newTrades = this.orderBooks.get(stockSymbol).matchOrders();
        newTrades.forEach(t -> this.trades.put(t.getTradeOrderId(), t));
        return order.getOrderId();
    }

}
