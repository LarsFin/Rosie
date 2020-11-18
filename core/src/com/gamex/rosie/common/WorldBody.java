package com.gamex.rosie.common;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.gamex.rosie.map.CheckResult;
import com.gamex.rosie.map.IMap;
import com.gamex.rosie.math.Vectors;

import static com.gamex.rosie.common.WorldConstants._2dDirections;

public class WorldBody implements IWorldBody {
    private Vector3 worldPosition = Vector3.Zero;
    private Vector3[] worldPositions;
    private final IMap map;

    public WorldBody(IMap map, Vector3[] startPositions) {

        this.map = map;
        worldPositions = startPositions;
    }

    public Vector3 getWorldPosition() {

        return worldPosition;
    }

    public Vector3[] getWorldPositions() {

        return worldPositions;
    }

    public void gravityUpdate() {

        Vector3 gravity = WorldPhysics.getNormalGravity();

        for (Vector3 point : worldPositions) {

            Vector3 pointAfterGravity = Vectors.add(point, gravity);

            if (!isSelfPositioned(pointAfterGravity) && map.checkEmptyRelative(point, gravity) != CheckResult.EMPTY) {

                return;
            }
        }

        map.putAtRelative(this, gravity);
        gravityUpdate();
    }

    public void setWorldPosition(Vector3 worldPosition) {

        this.worldPosition = worldPosition;
    }

    public void setWorldPositions(Vector3[] worldPositions) {

        this.worldPositions = worldPositions;
    }

    public void move(WorldConstants._2dDirection direction) {

        Vector2 movement = _2dDirections.get(direction);

        if (map.checkEmptyRelative(worldPosition, movement) == CheckResult.EMPTY)
            map.putAtRelative(this, movement);
    }

    private boolean isSelfPositioned(Vector3 pointCheck) {

        for (Vector3 point : worldPositions) {

            if (point.equals(pointCheck))
                return true;
        }

        return false;
    }
}