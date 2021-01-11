package com.gamex.rosie.controllers;

import java.util.HashMap;

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

        if (direction == RotationalDirection.CLOCKWISE) {

            orientation = clockwiseOrientations.get(orientation);
        } else {

            orientation = antiClockwiseOrientations.get(orientation);
        }
    }

    private final static HashMap<_3dDirection, _3dDirection> clockwiseOrientations;
    private final static HashMap<_3dDirection, _3dDirection> antiClockwiseOrientations;

    static {

        clockwiseOrientations = new HashMap<>();
        clockwiseOrientations.put(_3dDirection.NORTH, _3dDirection.EAST);
        clockwiseOrientations.put(_3dDirection.EAST, _3dDirection.SOUTH);
        clockwiseOrientations.put(_3dDirection.SOUTH, _3dDirection.WEST);
        clockwiseOrientations.put(_3dDirection.WEST, _3dDirection.NORTH);

        antiClockwiseOrientations = new HashMap<>();
        antiClockwiseOrientations.put(_3dDirection.NORTH, _3dDirection.WEST);
        antiClockwiseOrientations.put(_3dDirection.WEST, _3dDirection.SOUTH);
        antiClockwiseOrientations.put(_3dDirection.SOUTH, _3dDirection.EAST);
        antiClockwiseOrientations.put(_3dDirection.EAST, _3dDirection.NORTH);
    }
}
