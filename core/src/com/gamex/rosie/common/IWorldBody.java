package com.gamex.rosie.common;

import com.badlogic.gdx.math.Vector3;

public interface IWorldBody {

    Vector3 getWorldPosition();
    Vector3[] getWorldPositions();
    void gravityUpdate();
    void setWorldPosition(Vector3 worldPosition);
    void setWorldPositions(Vector3[] worldPositions);
}