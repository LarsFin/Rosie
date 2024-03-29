package com.gamex.rosie.map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.gamex.rosie.common.IWorldBody;

public interface IMap {

    CheckResult checkEmptyRelative(Vector3 position, Vector2 relativeOffset);
    CheckResult checkEmptyRelative(Vector3 position, Vector3 relativeOffset);
    IWorldBody getAtAbsolute(Vector3 absolutePosition);
    Vector3 getSize();
    void putAtAbsolute(IWorldBody worldBody, Vector3[] positions);
    void putAtRelative(IWorldBody worldBody, Vector2 relativePosition);
    void putAtRelative(IWorldBody worldBody, Vector3 relativePosition);
}