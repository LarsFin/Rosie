package com.gamex.rosie.controllers;

import com.badlogic.gdx.math.Vector3;
import com.gamex.rosie.common.IWorldBody;
import com.gamex.rosie.map.IMap;

import java.util.ArrayList;

public class MovementController implements IMovementController {

    private ArrayList<IWorldBody> checkedWorldBodies;
    private final IMap map;

    public MovementController(IMap map) {

        checkedWorldBodies = new ArrayList<>();
        this.map = map;
    }

    public void makeMovement(IWorldBody worldBody, Vector3 transformation) {

        if (canMakeMovement(worldBody, transformation)) {

            for (IWorldBody bodyToMove : checkedWorldBodies) {

                map.putAtRelative(bodyToMove, transformation);
            }
        }

        checkedWorldBodies = new ArrayList<>();
    }

    private boolean canMakeMovement(IWorldBody worldBody, Vector3 transformation) {

        if (checkedWorldBodies.contains(worldBody))
            return true;

        checkedWorldBodies.add(worldBody);

        if (!worldBody.isTransformationSafe(transformation))
            return false;

        ArrayList<IWorldBody> obstacleBodies = worldBody.getObstacles(transformation);

        for (IWorldBody obstacleBody : obstacleBodies) {

            boolean isStatic = obstacleBody.isStatic();
            boolean isTooHeavy = obstacleBody.getWeight() > worldBody.getWeight();

            if (isStatic || isTooHeavy)
                return false;

            if (!canMakeMovement(obstacleBody, transformation))
                return false;
        }

        return true;
    }
}