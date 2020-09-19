package com.gamex.rosie.math;

import com.badlogic.gdx.math.Vector3;

public final class Vectors {

    public static Vector3 add(Vector3 v1, Vector3 v2) {

        float x = v1.x + v2.x;
        float y = v1.y + v2.y;
        float z = v1.z + v2.z;

        return new Vector3(x, y, z);
    }
}