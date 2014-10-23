package com.simplify4me.failfast;

public interface FailFastCommand<T> {
    T execute();
}
