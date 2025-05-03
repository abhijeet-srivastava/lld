package com.twisha.stocktrader.service;

import com.twisha.stocktrader.entities.Order;
import com.twisha.stocktrader.entities.OrderType;
import com.twisha.stocktrader.entities.Trade;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.PriorityQueue;

public class StockOrderBook {
    private final String symbol;
    private final PriorityQueue<Order>  buyOrder;
    private final PriorityQueue<Order>  sellOrder;

    public StockOrderBook(String symbol) {
        this.symbol = symbol;
        this.buyOrder = new PriorityQueue<>((o1, o2) -> {
            int priceComparision = Double.compare(o2.getPrice(), o1.getPrice());
            return priceComparision != 0 ? priceComparision : o1.getTimestamp().compareTo(o2.getTimestamp());
        });

        this.sellOrder = new PriorityQueue<>(
                Comparator.comparingDouble(Order::getPrice).thenComparing(Order::getTimestamp)
        );
    }

    public synchronized void addOrder(Order order) {
        if (Objects.requireNonNull(order.getOrderType()) == OrderType.BUY) {
            this.buyOrder.offer(order);
        } else {
            this.sellOrder.offer(order);
        }
    }

    private synchronized void removeOrder(Order order) {
        if(order.getOrderType() == OrderType.BUY) {
            this.buyOrder.remove(order);
        } else {
            this.sellOrder.remove(order);
        }
    }
    public synchronized List<Trade> matchOrders() {
        List<Trade> trades = new ArrayList<>();
        while(!this.buyOrder.isEmpty()
                && !this.sellOrder.isEmpty()
                && this.buyOrder.peek().getPrice() >= this.sellOrder.peek().getPrice()) {
            Order buyOrder = this.buyOrder.remove();
            Order sellOrder = this.sellOrder.remove();
            int quantity = Math.min(buyOrder.getQuantity(), sellOrder.getQuantity());
            String tradeId = "T" + System.nanoTime();

            Trade trade = new Trade(
                    tradeId, buyOrder.getOrderId(), sellOrder.getOrderId(), this.symbol, quantity, sellOrder.getPrice(), LocalDateTime.now()
            );
            trades.add(trade);
            if(buyOrder.getQuantity() > quantity) {
                    Order remBuyOrder = new Order(
                            buyOrder.getOrderId(),
                            buyOrder.getUserId(),
                            OrderType.BUY,
                            buyOrder.getStockSymbol(),
                            buyOrder.getQuantity() - quantity,
                            buyOrder.getPrice(),
                            buyOrder.getTimestamp()
                    );
                    this.buyOrder.offer(remBuyOrder);
            } else if(sellOrder.getQuantity() > quantity) {
                Order remSellOrder = new Order(
                        sellOrder.getOrderId(),
                        sellOrder.getUserId(),
                        OrderType.SELL,
                        sellOrder.getStockSymbol(),
                        sellOrder.getQuantity() - quantity,
                        sellOrder.getPrice(),
                        sellOrder.getTimestamp()
                );
                this.buyOrder.offer(remSellOrder);
            }
        }
        return trades;
    }

    public synchronized Optional<Order> findOrder(String orderId) {
        for(Order order: this.buyOrder) {
            if(Objects.equals(order.getOrderId(), orderId)) {
                return Optional.of(order);
            }
        }
        for(Order order: this.sellOrder) {
            if(Objects.equals(order.getOrderId(), orderId)) {
                return Optional.of(order);
            }
        }
        return Optional.empty();
    }

}
