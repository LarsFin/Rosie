package com.gamex.rosie.map;

import com.badlogic.gdx.math.Vector3;

public interface IMap {
    CheckResult checkEmptyRelative(Vector3 position, Vector3 relativeOffset);
}

enum CheckResult {

    EMPTY,
    OCCUPIED
}