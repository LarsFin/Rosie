package com.gamex.rosie.controllers;

import static com.gamex.rosie.common.WorldConstants._3dDirection;
import static com.gamex.rosie.common.WorldConstants.RotationalDirection;

public class CameraController implements ICameraController {

    private _3dDirection orientation;

    public CameraController() {

        orientation = _3dDirection.NORTH;
    }

    public CameraController(_3dDirection initialOrientation) {

        orientation = initialOrientation;
    }

    public _3dDirection getOrientation() {

        return orientation;
    }

    public void turn(RotationalDirection direction) {

        // TODO
    }
}
