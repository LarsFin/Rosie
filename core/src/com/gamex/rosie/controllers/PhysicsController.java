package com.gamex.rosie.controllers;

import com.badlogic.gdx.math.Vector3;
import com.gamex.rosie.common.Factories.Factory2;
import com.gamex.rosie.common.IWorldBody;
import com.gamex.rosie.common.Transformation;
import com.gamex.rosie.common.WorldConstants._3dDirection;
import com.gamex.rosie.map.IMap;

import java.util.ArrayList;
import java.util.List;

import static com.gamex.rosie.common.WorldConstants._3dDirections;

public class PhysicsController implements IPhysicsController {

    private final _3dDirection gravityDirection = _3dDirection.DOWN;
    private final IMap map;
    private final Factory2<Transformation, IWorldBody, Vector3> transformationFactory;

    public PhysicsController(IMap map, Factory2<Transformation, IWorldBody, Vector3> transformationFactory) {

        this.map = map;
        this.transformationFactory = transformationFactory;
    }

    public Vector3 getGravity() {

        return _3dDirections.get(gravityDirection);
    }

    public List<Transformation> getGravityTransformations() {

        ArrayList<Transformation> transformations = new ArrayList<>();
        Vector3 mapBounds = map.getSize();

        for (int z = 0; z < mapBounds.z; z++) {

            for (int y = 0; y < mapBounds.y; y++) {

                for (int x = 0; x < mapBounds.x; x++) {

                    IWorldBody worldBody = map.getAtAbsolute(new Vector3(x, y, z));

                    if (shouldSkipBody(transformations, worldBody))
                        continue;

                    transformations.add(transformationFactory.build(worldBody, getGravity()));
                }
            }
        }

        return transformations;
    }

    private boolean shouldSkipBody(List<Transformation> transformations, IWorldBody worldBody) {

        if (worldBody == null || worldBody.isStatic())
            return true;

        for (Transformation transformation : transformations) {

            if (transformation.getReactingWorldBody().equals(worldBody))
                return true;
        }

        return false;
    }
}