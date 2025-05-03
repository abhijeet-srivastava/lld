package com.test.assignments.service;

import com.test.assignments.model.MoveDirection;
import com.test.assignments.model.Request;
import com.test.assignments.strategy.NextRequestStrategy;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

public class Elevator {

    private static final Integer NUM_FLOORS = 20;
    private int id;
    private MoveDirection state;
    private int currentFloor;

    private NextRequestStrategy nextRequestStrategy;
    Queue<Request> pendingRequests;

    public Elevator(int id, NextRequestStrategy  nextRequestStrategy) {
        this.id = id;
        this.state = MoveDirection.IDLE;
        this.pendingRequests = new LinkedList<>();
        this.nextRequestStrategy = nextRequestStrategy;
        this.currentFloor = 0;
    }

    public MoveDirection getState() {
        return state;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public Queue<Request> getPendingRequests() {
        return pendingRequests;
    }

    public int getId() {
        return id;
    }

    public synchronized  void addToElevatorQueue(Request request) {
        System.out.println("Elevator " + id + " added request: " + request);
        this.pendingRequests.add(request);
        notifyAll();
    }


    public void run() {
        processRequests();
    }


    private synchronized void processRequests() {
        while (true) {
            try {
                while (this.pendingRequests.isEmpty()) {
                    wait();
                }
                Optional<Request> nextRequest = nextRequestStrategy.findNextRequestToServe(this);
                serveNextRequest(nextRequest);
                notifyAll();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void serveNextRequest(Optional<Request> nextRequestOpt) {
        if(!nextRequestOpt.isPresent()) {
            return;
        }
        String threadName = Thread.currentThread().getName();
        Request nextRequest = nextRequestOpt.get();
        System.out.printf("Serving next request - %s\n", nextRequest.toString());
        updateMovementDirection(nextRequest.getSourceFloor());
        if(this.currentFloor != nextRequest.getSourceFloor()) {
            System.out.printf("[%s] Going to source floor: %d\n", threadName, nextRequest.getSourceFloor());
            do {
                updateFloor();
                System.out.printf("[%s] Reached to floor: %d\n", threadName, this.currentFloor);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } while (!reachedToDestination(nextRequest.getSourceFloor()));
        }
        System.out.printf("[%s] Going to dest floor: %d\n", threadName, nextRequest.getDestinationFloor());
        do {
            updateFloor();
            System.out.printf("[%s] Reached to floor: %d\n", threadName, this.currentFloor);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } while (!reachedToDestination(nextRequest.getDestinationFloor()));
        this.pendingRequests.remove(nextRequest);
    }

    private boolean reachedToDestination(int destFloor) {
        return this.currentFloor == destFloor;
    }

    private void updateFloor() {
        this.currentFloor = this.state.updateFloor(this.currentFloor);
    }

    private void updateMovementDirection(int destinationFloor) {
        if(this.currentFloor < destinationFloor) {
            this.state = MoveDirection.MOVE_UP;
        } else if(this.currentFloor > destinationFloor) {
            this.state = MoveDirection.MOVE_DOWN;
        }
    }

    public boolean canHandleRequest(Request req) {
        if(this.state == MoveDirection.IDLE) {
            return true;
        } else if(state == MoveDirection.MOVE_UP && req.getDirection() == MoveDirection.MOVE_UP
                && req.getSourceFloor() >= this.currentFloor) {
            return true;
        } else if(state == MoveDirection.MOVE_DOWN && req.getDirection() == MoveDirection.MOVE_DOWN
                && req.getSourceFloor() <= this.currentFloor) {
            return true;
        }
        return false;
    }
}
