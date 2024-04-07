package com.test.assignments;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CoffeeService {

    List<CofeeFlavours> falvorsSupported;

    Map<CofeeInventory, Double> availableQuantity;

    public CoffeeService(List<CofeeFlavours> falvorsSupported, Map<CofeeInventory, Double> availableQuantity) {
        this.falvorsSupported = falvorsSupported;
        this.availableQuantity = availableQuantity;
        startInventoryManagementThread();
    }

    private void startInventoryManagementThread() {
        Runnable maintenace = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    for(Map.Entry<CofeeInventory, Double> availbleQunat: availableQuantity.entrySet()) {
                        if(availbleQunat.getValue() < 0.5) {
                            System.out.printf("%s is less then thrshold quantity", availbleQunat.getKey().toString());
                        }
                    }
                }
            }
        };
        Thread maintenaceTread = new Thread(maintenace);
        maintenaceTread.start();
    }

    List<CofeeFlavours> cataluge() {
        List<CofeeFlavours> availableFalvors = new ArrayList<>();
        for(CofeeFlavours cf: falvorsSupported) {
            if(enoughQuantityAvailable(cf)) {
                availableFalvors.add(cf);
            }
        }
        return availableFalvors;
    }

    public Cofee dispose(CofeeFlavours cf, CofeeSize size) {
        if(!enoughQuantityAvailable(cf)) {
            throw new RuntimeException("Enough inventory is not avalable");
        }
        reduceInventory(cf);
        prepareCofee();
        return new Cofee(cf, size);
    }

    private void reduceInventory(CofeeFlavours cf) {
        for(Map.Entry<CofeeInventory, Double> requiredQuantity : cf.inventoryQuantity.entrySet()) {
            availableQuantity.merge(requiredQuantity.getKey(), -1*requiredQuantity.getValue(), Double::sum);
        }
    }

    private void prepareCofee() {
    }

    private boolean enoughQuantityAvailable(CofeeFlavours cf) {
        for(Map.Entry<CofeeInventory, Double> requiredQuantity : cf.inventoryQuantity.entrySet()) {
            if(availableQuantity.get(requiredQuantity.getKey()) >= requiredQuantity.getValue()) {
                return true;
            }
        }
        return false;
    }
}
