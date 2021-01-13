package com.gamex.rosie.controllers;

import com.badlogic.gdx.math.Vector3;
import com.gamex.rosie.common.Factories.Factory;
import com.gamex.rosie.common.IWorldBody;
import com.gamex.rosie.common.Transformation;
import com.gamex.rosie.common.WorldConstants._3dDirection;
import com.gamex.rosie.map.IMap;

import java.util.List;

import static com.gamex.rosie.common.WorldConstants._3dDirections;

public class PhysicsController implements IPhysicsController {

    private final _3dDirection gravityDirection = _3dDirection.DOWN;
    private final IMap map;
    private final Factory<Transformation, IWorldBody, Vector3> transformationFactory;

    public PhysicsController(IMap map, Factory<Transformation, IWorldBody, Vector3> transformationFactory) {

        this.map = map;
        this.transformationFactory = transformationFactory;
    }

    public Vector3 getGravity() {

        return _3dDirections.get(gravityDirection);
    }

    public List<Transformation> getGravityTransformations() {

        return null;
    }
}