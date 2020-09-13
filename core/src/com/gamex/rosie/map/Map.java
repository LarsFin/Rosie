package com.gamex.rosie.map;

import com.badlogic.gdx.math.Vector3;
import com.gamex.rosie.common.IWorldBody;

public class Map implements IMap {

    private final int width;
    private final int length;
    private final int height;

    private final float tileSize;
    private final IWorldBody[][][] bodyMap;

    public Map(float tileSize, Vector3 mapSize) {

        this.tileSize = tileSize;

        width = (int) mapSize.x;
        length = (int) mapSize.y;
        height = (int) mapSize.z;

        bodyMap = new IWorldBody[width][length][height];
    }

    public CheckResult checkEmptyRelative(Vector3 position, Vector3 relativeOffset) {

        Vector3 absolutePosition = position.add(relativeOffset);

        if (!isAbsoluteInBounds(absolutePosition))
            return CheckResult.OUT_OF_BOUNDS;

        if (getAtAbsolute(absolutePosition) != null)
            return CheckResult.OCCUPIED;

        return CheckResult.EMPTY;
    }

    public void putAtAbsolute(Vector3 position, IWorldBody worldBody) {

        int x = (int) position.x;
        int y = (int) position.y;
        int z = (int) position.z;

        bodyMap[x][y][z] = worldBody;
        worldBody.setWorldPosition(position);
    }

    private IWorldBody getAtAbsolute(Vector3 absolutePosition) {

        int x = (int) absolutePosition.x;
        int y = (int) absolutePosition.y;
        int z = (int) absolutePosition.z;

        return bodyMap[x][y][z];
    }

    private boolean isAbsoluteInBounds(Vector3 absolutePosition) {

        int x = (int) absolutePosition.x;
        int y = (int) absolutePosition.y;
        int z = (int) absolutePosition.z;

        return x >= 0 && x < width &&
                y >= 0 && y < length &&
                z >= 0 && z < height;
    }
}