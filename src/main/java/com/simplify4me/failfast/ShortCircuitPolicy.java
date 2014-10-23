package com.simplify4me.failfast;

public interface ShortCircuitPolicy<T> {

    boolean shortCircuit();

    void notifyExecutionStatus(ExecutionStatus status);
}
