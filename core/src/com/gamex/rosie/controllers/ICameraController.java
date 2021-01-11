package com.gamex.rosie.controllers;

import static com.gamex.rosie.common.WorldConstants._3dDirection;
import static com.gamex.rosie.common.WorldConstants.RotationalDirection;

public interface ICameraController {

    _3dDirection getOrientation();
    void turn(RotationalDirection direction);
}
