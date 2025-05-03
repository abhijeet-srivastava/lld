package com.test.assignments.strategy;

import com.test.assignments.model.Request;
import com.test.assignments.service.Elevator;

import java.util.Optional;

public interface NextRequestStrategy {
    Optional<Request> findNextRequestToServe(Elevator elevator);
}
