package com.gamex.rosie.common;

import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;

public final class WorldConstants {
    public enum Direction {
        UP,
        RIGHT,
        DOWN,
        LEFT
    }

    public static HashMap<Direction, Vector2> Directions;

    static {
        Directions = new HashMap<>();
        Directions.put(Direction.UP, new Vector2(0, 1));
        Directions.put(Direction.RIGHT, new Vector2(1, 0));
        Directions.put(Direction.DOWN, new Vector2(0, -1));
        Directions.put(Direction.LEFT, new Vector2(-1, 0));
    }
}
