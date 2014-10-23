package com.simplify4me.failfast;

import com.simplify4me.failfast.util.Config;

public class ManualShortCircuitPolicy extends AbstractShortCircuitPolicy {

    private Config<Boolean> config = null;

    public ManualShortCircuitPolicy(Config<Boolean> config) {
        this(null, config);
    }

    public ManualShortCircuitPolicy(ShortCircuitPolicy policy, Config<Boolean> config) {
        super(policy);
        this.config = config;
    }

    @Override
    protected boolean checkShortCircuit() {
        return config.get().booleanValue();
    }
}
