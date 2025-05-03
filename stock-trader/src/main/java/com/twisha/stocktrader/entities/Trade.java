package com.twisha.stocktrader.entities;

import java.time.LocalDateTime;

public class Trade {
    private final String tradeOrderId;
    private final String buyOrderId;
    private final String sellOrderId;

    private final String stockSymbol;
    private final int quantity;
    private final double price;

    private final LocalDateTime timestamp;

    public Trade(String tradeOrderId, String buyOrderId, String sellOrderId, String stockSymbol, int quantity, double price, LocalDateTime timestamp) {
        this.tradeOrderId = tradeOrderId;
        this.buyOrderId = buyOrderId;
        this.sellOrderId = sellOrderId;
        this.stockSymbol = stockSymbol;
        this.quantity = quantity;
        this.price = price;
        this.timestamp = timestamp;
    }

    public String getTradeOrderId() {
        return tradeOrderId;
    }

    public String getBuyOrderId() {
        return buyOrderId;
    }

    public String getSellOrderId() {
        return sellOrderId;
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
}
