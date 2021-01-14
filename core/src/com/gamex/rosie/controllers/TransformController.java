package com.gamex.rosie.controllers;

import com.badlogic.gdx.math.Vector3;
import com.gamex.rosie.common.Factories.*;
import com.gamex.rosie.common.IWorldBody;
import com.gamex.rosie.common.Transformation;
import com.gamex.rosie.common.TransformationResult;
import com.gamex.rosie.map.IMap;

import java.util.ArrayList;
import java.util.List;

import static com.gamex.rosie.common.Transformation.hasConsideration;
import static com.gamex.rosie.common.Transformation.Consideration.*;

public class TransformController implements ITransformController {

    private ArrayList<IWorldBody> checkedWorldBodies;
    private ArrayList<Transformation> attemptedTransformations;
    private final IMap map;
    private final Factory2<TransformationResult, Boolean, List<Transformation>> transformationResultFactory;
    private final Factory3<Transformation, IWorldBody, IWorldBody, Vector3> transformationFactory;

    public TransformController(IMap map,
                               Factory2<TransformationResult, Boolean, List<Transformation>> transformationResultFactory,
                               Factory3<Transformation, IWorldBody, IWorldBody, Vector3> transformationFactory) {

        checkedWorldBodies = new ArrayList<>();
        attemptedTransformations = new ArrayList<>();

        this.map = map;
        this.transformationResultFactory = transformationResultFactory;
        this.transformationFactory = transformationFactory;
    }

    public TransformationResult applyTransform(Transformation transformation, Transformation.Consideration[] considerations) {

        TransformationResult result;
        attemptedTransformations.add(transformation);

        IWorldBody worldBody = transformation.getReactingWorldBody();
        Vector3 displacement = transformation.getDisplacement();

        if (canApplyTransform(worldBody, displacement, considerations)) {

            for (IWorldBody bodyToMove : checkedWorldBodies) {

                map.putAtRelative(bodyToMove, displacement);
            }

            result = transformationResultFactory.build(true, attemptedTransformations);
        } else {

            result = transformationResultFactory.build(false, attemptedTransformations);
        }

        checkedWorldBodies = new ArrayList<>();
        attemptedTransformations = new ArrayList<>();

        return result;
    }

    private boolean canApplyTransform(IWorldBody worldBody,
                                      Vector3 displacement,
                                      Transformation.Consideration[] considerations) {

        checkedWorldBodies.add(worldBody);

        if (hasConsideration(considerations, STATIC) && worldBody.isStatic())
            return false;

        if (!worldBody.isTransformationSafe(displacement))
            return false;

        ArrayList<IWorldBody> obstacleBodies = worldBody.getObstacles(displacement);

        for (IWorldBody obstacleBody : obstacleBodies) {

            if (checkedWorldBodies.contains(obstacleBody))
                continue;

            attemptedTransformations.add(transformationFactory.build(worldBody, obstacleBody, displacement));

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