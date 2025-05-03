package com.test.assignments.model;

import java.util.Objects;

public  class Request {

    private Integer requestId;
    private  final int sourceFloor;
    private  final int destinationFloor;
    private final  MoveDirection direction;



    public Request(int sourceFloor, int destinationFloor) {
        this.sourceFloor = sourceFloor;
        this.destinationFloor = destinationFloor;
        this.direction = this.sourceFloor < this.destinationFloor ? MoveDirection.MOVE_UP : MoveDirection.MOVE_DOWN;
    }

    public int getSourceFloor() {
        return sourceFloor;
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }

    public MoveDirection getDirection() {
        return direction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return Objects.equals(requestId, request.requestId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestId);
    }

    @Override
    public String toString() {
        return "Going from floor num[" + sourceFloor + "] to dest floor [" + destinationFloor + "] in Direction :" + direction;
    }
}
