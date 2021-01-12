package com.gamex.rosie.controllers;

import com.badlogic.gdx.math.Vector3;
import com.gamex.rosie.common.WorldConstants._3dDirection;
import com.gamex.rosie.math.Degrees;

import static com.gamex.rosie.common.WorldConstants._3dDirections;

public class PlayerController implements IPlayerController {

    public Vector3 getMovement(_3dDirection inputDirection, _3dDirection cameraOrientation) {

        if (inputDirection == _3dDirection.NONE)
            return Vector3.Zero;

        float inputDegrees = Degrees.from3dDirection(inputDirection);
        float cameraDegrees = Degrees.from3dDirection(cameraOrientation);

        float resultantDegrees = Degrees.sumDegrees(inputDegrees, cameraDegrees);
        _3dDirection resultantDirection = Degrees.as3dDirection(resultantDegrees);

        return _3dDirections.get(resultantDirection);
    }
}