package com.gamex.rosie.common;

import com.badlogic.gdx.math.Vector3;

public class Transformation {

    private IWorldBody initiatingWorldBody;
    private IWorldBody reactingWorldBody;
    private Vector3 displacement;

    public void Transformation(IWorldBody initiatingWorldBody, IWorldBody reactingWorldBody, Vector3 displacement) {

        this.initiatingWorldBody = initiatingWorldBody;
        this.reactingWorldBody = reactingWorldBody;
        this.displacement = displacement;
    }

    public void Transformation(IWorldBody worldBody, Vector3 displacement) {

        this.reactingWorldBody = worldBody;
        this.displacement = displacement;
    }

    public IWorldBody getInitiatingWorldBody() {

        return initiatingWorldBody;
    }

    public IWorldBody getReactingWorldBody() {

        return reactingWorldBody;
    }

    public Vector3 getDisplacement() {

        return displacement;
    }

    public static Consideration[] allConsiderations() {

        return Consideration.values();
    }

    public static boolean hasConsideration(Consideration[] considerations, Consideration considerationToCheck) {

        for (Consideration consideration : considerations) {

            if (consideration == considerationToCheck) {

                return true;
            }
        }

        return false;
    }

    public enum Consideration {

        WEIGHT,
        STATIC
    }
}