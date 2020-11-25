package com.gamex.rosie.movement;

import com.badlogic.gdx.math.Vector3;
import com.gamex.rosie.common.IWorldBody;

public interface IMovementController {

    void makeMovement(IWorldBody worldBody, Vector3 transformation);
}