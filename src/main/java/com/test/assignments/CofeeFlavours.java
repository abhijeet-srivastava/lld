package com.test.assignments;


import java.util.HashMap;
import java.util.Map;

public class CofeeFlavours {

    String falvourName;

    Map<CofeeInventory, Double> inventoryQuantity;

    public CofeeFlavours(String name, Map<CofeeInventory, Double> inventoryQuantity) {
        this.falvourName = name;
        this.inventoryQuantity = new HashMap<>();
        for(Map.Entry<CofeeInventory, Double> t: inventoryQuantity.entrySet()) {
            this.inventoryQuantity.put(t.getKey(), t.getValue());
        }

    }
}
