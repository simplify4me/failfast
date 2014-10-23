package com.simplify4me.failfast;

public abstract class AbstractShortCircuitPolicy implements ShortCircuitPolicy {
    protected ShortCircuitPolicy shortCircuitPolicy = null;

    protected AbstractShortCircuitPolicy() {
    }

    protected AbstractShortCircuitPolicy(ShortCircuitPolicy shortCircuitPolicy) {
        this.shortCircuitPolicy = shortCircuitPolicy;
    }

    public boolean shortCircuit() {
        if (this.shortCircuitPolicy != null && this.shortCircuitPolicy.shortCircuit()) return true;
        return checkShortCircuit();
    }

    protected abstract boolean checkShortCircuit();

    public void notifyExecutionStatus(ExecutionStatus status) {
        if (this.shortCircuitPolicy != null) this.shortCircuitPolicy.notifyExecutionStatus(status);
    }
}
