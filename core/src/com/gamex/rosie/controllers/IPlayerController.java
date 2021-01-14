package com.gamex.rosie.controllers;

import com.badlogic.gdx.math.Vector3;
import com.gamex.rosie.common.Transformation;
import com.gamex.rosie.common.WorldConstants._3dDirection;

public interface IPlayerController {

    Vector3 getMovement(_3dDirection inputDirection, _3dDirection cameraOrientation);
    Transformation getTransformation(Vector3 displacement);
    _3dDirection resolveMovementDirection(int horizontalInputFeed, int verticalInputFeed);
}