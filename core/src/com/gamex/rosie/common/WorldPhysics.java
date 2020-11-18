package com.gamex.rosie.common;

import com.badlogic.gdx.math.Vector3;

public final class WorldPhysics {

    private static WorldConstants._3dDirection gravityDirection;

    private WorldPhysics() {

        gravityDirection = WorldConstants._3dDirection.DOWN;
    }

    public static WorldConstants._3dDirection getGravityDirection() {

        return gravityDirection;
    }

    public static void setGravityDirection(WorldConstants._3dDirection direction) {

        gravityDirection = direction;
    }

    public static Vector3 getNormalGravity() {

        return WorldConstants._3dDirections.get(gravityDirection);
    }
}