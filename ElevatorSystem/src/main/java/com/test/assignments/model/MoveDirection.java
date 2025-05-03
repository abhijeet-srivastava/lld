package com.test.assignments.model;

public enum MoveDirection {
    
    IDLE{
        @Override
        public int updateFloor(int currFloor) {
            return currFloor;
        }
    },
    MOVE_UP{
        @Override
        public int updateFloor(int currFloor) {
            return currFloor+1;
        }
    },
    MOVE_DOWN{
        @Override
        public int updateFloor(int currFloor) {
            return currFloor-1;
        }
    };

    public abstract int updateFloor(int currentFloor);
}
