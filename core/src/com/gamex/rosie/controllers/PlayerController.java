package com.gamex.rosie.controllers;

import com.badlogic.gdx.math.Vector3;
import com.gamex.rosie.common.Factories.Factory2;
import com.gamex.rosie.common.IWorldBody;
import com.gamex.rosie.common.Transformation;
import com.gamex.rosie.common.WorldConstants._3dDirection;
import com.gamex.rosie.math.Degrees;

import static com.gamex.rosie.common.WorldConstants._3dDirections;

public class PlayerController implements IPlayerController {

    private final IWorldBody controlledWorldBody;
    private final Factory2<Transformation, IWorldBody, Vector3> transformationFactory;

    public PlayerController(IWorldBody controlledWorldBody,
                            Factory2<Transformation, IWorldBody, Vector3> transformationFactory) {

        this.controlledWorldBody = controlledWorldBody;
        this.transformationFactory = transformationFactory;
    }

    public Vector3 getMovement(_3dDirection inputDirection, _3dDirection cameraOrientation) {

        if (inputDirection == _3dDirection.NONE)
            return Vector3.Zero;

        float inputDegrees = Degrees.from3dDirection(inputDirection);
        float cameraDegrees = Degrees.from3dDirection(cameraOrientation);

        float resultantDegrees = Degrees.sumDegrees(inputDegrees, cameraDegrees);
        _3dDirection resultantDirection = Degrees.as3dDirection(resultantDegrees);

        return _3dDirections.get(resultantDirection);
    }

    public Transformation getTransformation(Vector3 displacement) {

        return transformationFactory.build(controlledWorldBody, displacement);
    }

    public _3dDirection resolveMovementDirection(int horizontalInputFeed, int verticalInputFeed) {

        if (verticalInputFeed > 0)
            return _3dDirection.NORTH;

        if (horizontalInputFeed > 0)
            return _3dDirection.EAST;

        if (verticalInputFeed < 0)
            return _3dDirection.SOUTH;

        if (horizontalInputFeed < 0)
            return _3dDirection.WEST;

        return _3dDirection.NONE;
    }
}