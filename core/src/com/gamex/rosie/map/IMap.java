package com.gamex.rosie.map;

import com.badlogic.gdx.math.Vector3;
import com.gamex.rosie.common.IWorldBody;

public interface IMap {
    CheckResult checkEmptyRelative(Vector3 position, Vector3 relativeOffset);
    IWorldBody getAtAbsolute(Vector3 absolutePosition);
    void putAtAbsolute(IWorldBody worldBody, Vector3 position);
    void putAtRelative(IWorldBody worldBody, Vector3 relativePosition);
}