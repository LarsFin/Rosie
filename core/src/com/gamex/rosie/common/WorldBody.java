package com.gamex.rosie.common;

import com.badlogic.gdx.math.Vector3;
import com.gamex.rosie.map.Map;

public class WorldBody implements IWorldBody {
    private Vector3 worldPosition;
    private Map map;

    public void setWorldPosition(Vector3 worldPosition) {

        this.worldPosition = worldPosition;
    }
}