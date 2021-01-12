package com.gamex.rosie.math;

import static com.gamex.rosie.common.WorldConstants._3dDirection;

public final class Degrees {

    public static _3dDirection as3dDirection(float degrees) {

        degrees = normalizeDegrees(degrees);

        if (degrees >= 315f || degrees < 45f)
            return _3dDirection.NORTH;

        if (degrees >= 45f && degrees < 135f)
            return _3dDirection.EAST;

        if (degrees >= 135f && degrees < 225f)
            return _3dDirection.SOUTH;

        return _3dDirection.WEST;
    }

    public static float from3dDirection(_3dDirection direction) {

        switch (direction) {

            case NORTH:
                return 0f;

            case EAST:
                return 90f;

            case SOUTH:
                return 180f;

            case WEST:
                return 270f;

            default:
                return -1f;
        }
    }

    public static float sumDegrees(float... degreesValues) {

        float sum = 0f;

        for (float degrees : degreesValues) {

            sum += degrees;
        }

        return normalizeDegrees(sum);
    }

    private static float normalizeDegrees(float degrees) {

        degrees %= 360f;

        if (degrees < 0f)
            degrees += 360f;

        return degrees;
    }
}