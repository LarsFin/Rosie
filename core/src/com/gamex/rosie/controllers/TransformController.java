package com.gamex.rosie.controllers;

import com.badlogic.gdx.math.Vector3;
import com.gamex.rosie.common.IWorldBody;
import com.gamex.rosie.common.Transformation;
import com.gamex.rosie.map.IMap;

import java.util.ArrayList;

import static com.gamex.rosie.common.Transformation.hasConsideration;
import static com.gamex.rosie.common.Transformation.Consideration.*;

public class TransformController implements ITransformController {

    private ArrayList<IWorldBody> checkedWorldBodies;
    private final IMap map;

    public TransformController(IMap map) {

        checkedWorldBodies = new ArrayList<>();
        this.map = map;
    }

    public void applyTransform(Transformation transformation, Transformation.Consideration[] considerations) {

        IWorldBody worldBody = transformation.getWorldBody();
        Vector3 displacement = transformation.getDisplacement();

        if (canApplyTransform(worldBody, displacement, considerations)) {

            for (IWorldBody bodyToMove : checkedWorldBodies) {

                map.putAtRelative(bodyToMove, displacement);
            }
        }

        checkedWorldBodies = new ArrayList<>();
    }

    private boolean canApplyTransform(IWorldBody worldBody,
                                      Vector3 displacement,
                                      Transformation.Consideration[] considerations) {

        if (checkedWorldBodies.contains(worldBody))
            return true;

        checkedWorldBodies.add(worldBody);

        if (hasConsideration(considerations, STATIC) && worldBody.isStatic())
            return false;

        if (!worldBody.isTransformationSafe(displacement))
            return false;

        ArrayList<IWorldBody> obstacleBodies = worldBody.getObstacles(displacement);

        for (IWorldBody obstacleBody : obstacleBodies) {

            if (hasConsideration(considerations, WEIGHT)) {

                boolean isTooHeavy = obstacleBody.getWeight() > worldBody.getWeight();

                if (isTooHeavy)
                    return false;
            }

            if (!canApplyTransform(obstacleBody, displacement, considerations))
                return false;
        }

        return true;
    }
}