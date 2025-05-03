package com.test.assignments.repository;

import com.test.assignments.model.StockOrder;
import com.test.assignments.model.StockOrderType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class PendingStockOrdersRepo {

    private  Map<String, TreeMap<Double, LinkedHashSet<StockOrder>>> PENDING_BUY_ORDERS = null;
    private Map<String, TreeMap<Double, LinkedHashSet<StockOrder>>> PENDING_SELL_ORDERS = null;

    public PendingStockOrdersRepo() {
        this.PENDING_BUY_ORDERS = new HashMap<>();
        this.PENDING_SELL_ORDERS = new HashMap<>();
    }

    public List<StockOrder> fetchMatchingSellOrder(StockOrder buyOrder) {
        List<StockOrder> result = new ArrayList<>();
        if(!this.PENDING_SELL_ORDERS.containsKey(buyOrder.getSymbol())) {
            return result;
        }
        SortedMap<Double, LinkedHashSet<StockOrder>> sellOrders
                = this.PENDING_SELL_ORDERS.get(buyOrder.getSymbol()).tailMap(buyOrder.getAskedPrice());
        if(sellOrders.isEmpty() || sellOrders.isEmpty()) {
            return result;
        }
        int requiredQuantity = buyOrder.getQuantity();
        while (requiredQuantity > 0) {
            Iterator<Map.Entry<Double, LinkedHashSet<StockOrder>>> itr = sellOrders.entrySet().iterator();
            while (itr.hasNext() && requiredQuantity > 0) {
                Map.Entry<Double, LinkedHashSet<StockOrder>> next = itr.next();
                Iterator<StockOrder> stockOrderIterator = next.getValue().iterator();
                while(stockOrderIterator.hasNext()) {
                    StockOrder order = stockOrderIterator.next();
                    stockOrderIterator.remove();
                    if(order.getQuantity() <=  requiredQuantity) {
                        result.add(order);
                        requiredQuantity -= order.getQuantity();
                    } else {
                        StockOrder remainingSellOrder = StockOrder.builder()
                                .symbol(order.getSymbol())
                                .orderType(StockOrderType.SELL)
                                .askedPrice(order.getAskedPrice())
                                .quantity(order.getQuantity() - requiredQuantity)
                                .build();
                        StockOrder recordMatchingBuy = StockOrder.builder()
                                .symbol(order.getSymbol())
                                .orderType(StockOrderType.SELL)
                                .askedPrice(order.getAskedPrice())
                                .quantity( requiredQuantity)
                                .build();
                        result.add(recordMatchingBuy);
                        addSellingRecord(remainingSellOrder);
                        requiredQuantity = 0;
                    }
                }
            }
        }
        return result;
    }

    private void addSellingRecord(StockOrder remainingSellOrder) {
    }
}
