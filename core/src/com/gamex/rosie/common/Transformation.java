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