package com.gamex.rosie.common;

import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public interface IWorldBody {

    ArrayList<IWorldBody> getObstacles(Vector3 transformation);
    Vector3[] getWorldPosition();
    void gravityUpdate();
    void setWorldPosition(Vector3[] worldPositions);
}