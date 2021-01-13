package com.gamex.rosie.common;

import com.badlogic.gdx.math.Vector3;

public class Transformation {

    private IWorldBody worldBody;
    private Vector3 displacement;

    public void Transformation(IWorldBody worldBody, Vector3 displacement) {

        this.worldBody = worldBody;
        this.displacement = displacement;
    }

    public IWorldBody getWorldBody() {

        return worldBody;
    }

    public Vector3 getDisplacement() {

        return displacement;
    }
}