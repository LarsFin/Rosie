package com.gamex.rosie.common;

import com.badlogic.gdx.math.Vector3;
import com.gamex.rosie.map.IMap;

public class WorldBody implements IWorldBody {
    private Vector3 worldPosition = Vector3.Zero;
    private IMap map;

    public WorldBody(IMap map) {

        this.map = map;
    }

    public Vector3 getPosition() {

        return worldPosition;
    }

    public void gravityUpdate() {


    }

    public void setWorldPosition(Vector3 worldPosition) {

        this.worldPosition = worldPosition;
    }
}