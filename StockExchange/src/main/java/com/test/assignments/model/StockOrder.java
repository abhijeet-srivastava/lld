package com.test.assignments.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class StockOrder {
    String symbol;
    StockOrderType orderType;

    Double askedPrice;
    Integer quantity;
}
