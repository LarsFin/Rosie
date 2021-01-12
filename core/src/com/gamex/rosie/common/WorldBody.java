package com.gamex.rosie.common;

import com.badlogic.gdx.math.Vector3;
import com.gamex.rosie.map.CheckResult;
import com.gamex.rosie.map.IMap;
import com.gamex.rosie.math.Vectors;

import java.util.ArrayList;

public class WorldBody implements IWorldBody {

    private final boolean isStatic;
    private final IMap map;
    private final int weight;
    private Vector3[] worldPosition;

    public WorldBody(IMap map, Vector3[] startPositions, WorldBodyConfig config) {

        this.map = map;
        worldPosition = startPositions;

        this.isStatic = config.Static;
        this.weight = config.Weight;
    }

    public ArrayList<IWorldBody> getObstacles(Vector3 transformation) {

        ArrayList<IWorldBody> obstacles = new ArrayList<>();

        for (Vector3 point : worldPosition) {

            Vector3 pointAfterTransform = Vectors.add(point, transformation);
            IWorldBody obstacle = map.getAtAbsolute(pointAfterTransform);

            if (obstacle != this && !obstacles.contains(obstacle)) {

                obstacles.add(obstacle);
            }
        }

        return obstacles;
    }

    public int getWeight() {

        return weight;
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

    public boolean isStatic() {

        return isStatic;
    }

    public boolean isTransformationSafe(Vector3 transformation) {

        // iterate through positions, ensuring each position is in bounds

        return false;
    }

    public void setWorldPosition(Vector3[] worldPositions) {

        this.worldPosition = worldPositions;
    }

    private boolean isNotSelfPositioned(Vector3 pointCheck) {

        for (Vector3 point : worldPosition) {

            if (point.equals(pointCheck))
                return false;
        }

        return true;
    }
}