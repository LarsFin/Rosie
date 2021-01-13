package com.gamex.rosie.common;

public final class Factories {

    public interface Factory<R, P1, P2> {

        R build(P1 arg1, P2 arg2);
    }
}
