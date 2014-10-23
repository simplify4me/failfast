package com.simplify4me.failfast;

import java.util.concurrent.TimeUnit;

import com.simplify4me.failfast.util.Sampler;

public class ErrorCountBasedPolicy extends AbstractShortCircuitPolicy implements ShortCircuitPolicy {

    private int errorThresholdPct = 50; //50% error rate per timeUnit
    private int windows = 0; //n windows = errorThresholdPct per timeUnit for n timeUnits.

    private final Sampler total, failed;
    private ShortCircuitPolicy shortCircuitPolicy = null;

    public ErrorCountBasedPolicy(int errorThresholdPct, int windows, TimeUnit timeUnit) {
        this(null, errorThresholdPct, windows, timeUnit);
    }

    public ErrorCountBasedPolicy(ShortCircuitPolicy policy, int errorThresholdPct, int windows, TimeUnit timeUnit) {
        super(policy);
        this.errorThresholdPct = errorThresholdPct;
        this.windows = windows;
        this.total = new Sampler(1, this.windows, timeUnit);
        this.failed = new Sampler(this.windows, 1, timeUnit);
    }

    @Override
    protected boolean checkShortCircuit() {
        long numErrors = total.count(0) * (int)(errorThresholdPct/100);
        return failed.greaterThan(numErrors, windows);
    }

    @Override
    public void notifyExecutionStatus(ExecutionStatus status) {
        super.notifyExecutionStatus(status);
        if (!status.isSucceeded()) this.failed.increment();
    }
}
