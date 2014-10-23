package com.simplify4me.failfast.util;

import java.util.concurrent.TimeUnit;

public class Sampler {

    private volatile int currentWindow = 0;

    private long timeMs = 1000;
    private int sampleWindows = 3;

    private long lastUpdatedTime = System.currentTimeMillis();

    private final LongAdder[] samples;

    public Sampler(int sampleWindows) {
        this.sampleWindows = sampleWindows;
        this.samples = new LongAdder[this.sampleWindows];
        for (int win = 0; win < this.sampleWindows; win++) {
            this.samples[win] = new LongAdder();
        }
    }

    public Sampler(int sampleWindows, int time, TimeUnit timeUnit) {
        this(sampleWindows);
        this.timeMs = TimeUnit.MILLISECONDS.convert(time, timeUnit);
    }

    public void increment() {
        final long now = System.currentTimeMillis();
        if (now - lastUpdatedTime > timeMs) {
            lastUpdatedTime = now;
            if (++currentWindow >= sampleWindows) currentWindow = 0;
            this.samples[currentWindow].reset();
        }
        this.samples[currentWindow].increment();
    }

    public long count(int window) {
        return this.samples[window].sum();
    }

    public boolean greaterThan(long value, int numWindows) {
        int foundWindows = 0;
        for (LongAdder adder : samples) {
            if (adder != null && adder.sum() > value) foundWindows++;
        }
        return foundWindows >= numWindows;
    }
}
