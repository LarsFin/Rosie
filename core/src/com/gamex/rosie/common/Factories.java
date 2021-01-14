package com.gamex.rosie.common;

public final class Factories {

    public interface Factory2<R, P1, P2> {

        R build(P1 arg1, P2 arg2);
    }

    public interface Factory3<R, P1, P2, P3> {

        R build(P1 arg1, P2 arg2, P3 arg3);
    }
}
