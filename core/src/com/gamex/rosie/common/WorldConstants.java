package com.gamex.rosie.common;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.HashMap;

public final class WorldConstants {

    public enum _2dDirection {

        UP,
        RIGHT,
        DOWN,
        LEFT
    }

    public enum _3dDirection {

        NORTH,
        EAST,
        SOUTH,
        WEST,
        UP,
        DOWN,

        NONE
    }

    public enum RotationalDirection {

        CLOCKWISE,
        ANTI_CLOCKWISE
    }

    public static HashMap<_2dDirection, Vector2> _2dDirections;

    static {

        _2dDirections = new HashMap<>();
        _2dDirections.put(_2dDirection.UP, new Vector2(0, 1));
        _2dDirections.put(_2dDirection.RIGHT, new Vector2(1, 0));
        _2dDirections.put(_2dDirection.DOWN, new Vector2(0, -1));
        _2dDirections.put(_2dDirection.LEFT, new Vector2(-1, 0));
    }

    public static HashMap<_3dDirection, Vector3> _3dDirections;

    static {

        _3dDirections = new HashMap<>();
        _3dDirections.put(_3dDirection.NORTH, new Vector3(0, 1, 0));
        _3dDirections.put(_3dDirection.EAST, new Vector3(1, 0, 0));
        _3dDirections.put(_3dDirection.SOUTH, new Vector3(0, -1, 0));
        _3dDirections.put(_3dDirection.WEST, new Vector3(-1, 0, 0));
        _3dDirections.put(_3dDirection.UP, new Vector3(0, 0, 1));
        _3dDirections.put(_3dDirection.DOWN, new Vector3(0, 0, -1));
    }
}
