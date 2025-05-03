package com.test.assignments.service;

import com.test.assignments.model.Request;
import com.test.assignments.strategy.NearestRequestStrategy;
import com.test.assignments.strategy.NextRequestStrategy;

import java.util.ArrayList;
import java.util.List;

public class ElevatorManager {

    private List<Elevator> elevators;

    public ElevatorManager(int size) {
        this.elevators = new ArrayList<>(size);
        NextRequestStrategy strategy = new NearestRequestStrategy();
        for(int idx = 1; idx <= size; idx++) {
            elevators.add(new Elevator(idx, strategy));
        }
        for(Elevator elevator: elevators) {
            new Thread(elevator::run, String.format("ElevatorWorker-%d", elevator.getId())).start();
        }
    }
    private Elevator findNextElevator(Request request) {
        Elevator nearestElevator = null;
        int minDist = Integer.MAX_VALUE;
        for(Elevator elevator: this.elevators) {
            int currDist = Math.abs(elevator.getCurrentFloor() - request.getSourceFloor());
            if(currDist < minDist) {
                minDist = currDist;
                nearestElevator = elevator;
            }
        }
        return nearestElevator ;
    }

    public void requestElevator(int srcFloor, int destFloor) {
        Request request = new Request(srcFloor, destFloor);
        Elevator optimumElevator = findNextElevator(request);
        optimumElevator.addToElevatorQueue(request);
    }
}
