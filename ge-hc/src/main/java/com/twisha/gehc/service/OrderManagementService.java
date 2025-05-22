package com.twisha.gehc.service;

import com.twisha.gehc.model.InputEvent;
import com.twisha.gehc.model.OrderDetails;
import com.twisha.gehc.utils.MessageQueue;

public class OrderManagementService {

    private MessageQueue messageQueue;





    public void processOrder(OrderDetails order) {
        /***
         * OrderDetails-
         * 1. Block Inventory - Inventory Management
         * 2. SUCCESS/FAILURE -
         */

        InputEvent blockInventoryEvent = convertToBlockInventoryEvent(order);


    }

    private InputEvent convertToBlockInventoryEvent(OrderDetails order) {
        return null;
    }
}
