package com.twisha.stocktrader.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StockOrderBookTest {

    private StockOrderBook stockOrderBook;

    @BeforeEach
    void setup() {
        stockOrderBook = new StockOrderBook("TestSymbol");
    }

    @Test
    void addOrder() {
    }

    @Test
    void matchOrders() {
    }

    @Test
    void findOrder() {
    }
}