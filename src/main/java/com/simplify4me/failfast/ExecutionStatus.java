package com.simplify4me.failfast;

public class ExecutionStatus {
    private boolean succeeded = false,
        fallback = false;

    private long executionTime = 0;

    private final FailFastCommand command;
    private Exception exception = null;

    public ExecutionStatus(FailFastCommand command) {
        this.command = command;
    }

    public void setSucceeded(boolean succeeded) {
        this.succeeded = succeeded;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public FailFastCommand getCommand() {
        return command;
    }

    public boolean isSucceeded() {
        return succeeded;
    }

    public long getExecutionTime() {
        return executionTime;
    }
}
