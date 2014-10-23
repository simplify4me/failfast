package com.simplify4me.failfast;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;

import com.simplify4me.failfast.util.Sampler;

public class SamplerTest {

    @Test
    public void samplePerSecond() throws Exception {
        Sampler sampler = new Sampler(3, 1, TimeUnit.SECONDS);
        for (int index = 0; index < 30; index++) {
            sampler.increment();
            Thread.sleep(100); //approx 10 increments per second
        }

        Assert.assertTrue(sampler.greaterThan(9, 3));

        Thread.sleep(2000);
        for (int index = 0; index < 5; index++) sampler.increment();

        Assert.assertFalse(sampler.greaterThan(9, 3));

        Thread.sleep(1000);
        for (int index = 0; index < 5; index++) sampler.increment();
        Thread.sleep(1000);
        for (int index = 0; index < 5; index++) sampler.increment();

        Assert.assertTrue(sampler.greaterThan(4, 2));
    }
}
