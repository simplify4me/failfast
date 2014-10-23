package com.simplify4me.failfast;

//todo: metrics
public abstract class PolicyBasedCommand<T> implements FailFastCommand<T> {

    private ShortCircuitPolicy policy = null;

    public PolicyBasedCommand(ShortCircuitPolicy policy) {
        this.policy = policy;
    }

    public T execute() {
        if (policy.shortCircuit()) {
            return getFallback();
        }
        else {
            final ExecutionStatus status = new ExecutionStatus(this);
            try {
                long start = System.currentTimeMillis();
                final T retVal = doExecute();
                status.setExecutionTime(System.currentTimeMillis() - start);
                status.setSucceeded(true);
                return retVal;
            }
            catch (RuntimeException ex) {
                status.setException(ex);
                throw ex;
            }
            finally {
                policy.notifyExecutionStatus(status);
            }
        }
    }

    protected abstract T doExecute();

    protected T getFallback() {
        throw new UnsupportedOperationException("No fallback");
    }
}
