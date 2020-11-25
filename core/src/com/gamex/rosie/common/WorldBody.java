package com.gamex.rosie.common;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.gamex.rosie.map.CheckResult;
import com.gamex.rosie.map.IMap;
import com.gamex.rosie.math.Vectors;

import java.util.ArrayList;

import static com.gamex.rosie.common.WorldConstants._2dDirections;

public class WorldBody implements IWorldBody {

    private Vector3[] worldPosition;
    private final IMap map;

    public WorldBody(IMap map, Vector3[] startPositions) {

        this.map = map;
        worldPosition = startPositions;
    }

    public ArrayList<IWorldBody> getObstacles(Vector3 transformation) {

        // TODO: Implement

        return new ArrayList<>();
    }

    public Vector3[] getWorldPosition() {

        return worldPosition;
    }

    public void gravityUpdate() {

        Vector3 gravity = WorldPhysics.getNormalGravity();

        for (Vector3 point : worldPosition) {

            Vector3 pointAfterGravity = Vectors.add(point, gravity);

            if (isNotSelfPositioned(pointAfterGravity) && map.checkEmptyRelative(point, gravity) != CheckResult.EMPTY) {

                return;
            }
        }

        map.putAtRelative(this, gravity);
        gravityUpdate();
    }

    public void setWorldPosition(Vector3[] worldPositions) {

        this.worldPosition = worldPositions;
    }

    public void move(WorldConstants._2dDirection direction) {

        Vector2 movement = _2dDirections.get(direction);

        for (Vector3 point : worldPosition) {

            Vector3 pointAfterMovement = Vectors.add(point, movement);

            if (isNotSelfPositioned(pointAfterMovement) && map.checkEmptyRelative(point, movement) != CheckResult.EMPTY) {

                return;
            }
        }

        map.putAtRelative(this, movement);
    }

    private boolean isNotSelfPositioned(Vector3 pointCheck) {

        for (Vector3 point : worldPosition) {

            if (point.equals(pointCheck))
                return false;
        }

        return true;
    }
}