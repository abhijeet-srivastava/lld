package com.test.assignments.strategy;

import com.test.assignments.model.Request;
import com.test.assignments.service.Elevator;

import java.util.Optional;

public class NearestRequestStrategy implements NextRequestStrategy{

    @Override
    public Optional<Request> findNextRequestToServe(Elevator elevator) {
        synchronized (elevator.getPendingRequests()) {
            for(Request req: elevator.getPendingRequests()) {
                if(elevator.canHandleRequest(req)) {
                    elevator.getPendingRequests().remove(req);
                    return Optional.of(req);
                }
            }
            return Optional.of(elevator.getPendingRequests().poll());
        }
    }
}
