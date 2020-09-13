package com.gamex.rosie.common;

import com.badlogic.gdx.math.Vector3;

public interface IWorldBody {

    Vector3 getPosition();
    void gravityUpdate();
    void setWorldPosition(Vector3 worldPosition);
}