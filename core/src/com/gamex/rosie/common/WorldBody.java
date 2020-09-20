package com.gamex.rosie.common;

import com.badlogic.gdx.math.Vector3;
import com.gamex.rosie.map.CheckResult;
import com.gamex.rosie.map.IMap;

public class WorldBody implements IWorldBody {
    private Vector3 worldPosition = Vector3.Zero;
    private final IMap map;

    public WorldBody(IMap map) {

        this.map = map;
    }

    public Vector3 getWorldPosition() {

        return worldPosition;
    }

    public void gravityUpdate() {

        Vector3 gravity = WorldConstants._3dDirections.get(WorldConstants._3dDirection.DOWN);

        while (map.checkEmptyRelative(worldPosition, gravity) == CheckResult.EMPTY) {

            map.putAtRelative(this, gravity);
        }
    }

    public void setWorldPosition(Vector3 worldPosition) {

        this.worldPosition = worldPosition;
    }

    public void move(WorldConstants._2dDirection direction) {


    }
}