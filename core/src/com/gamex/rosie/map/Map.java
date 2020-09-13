package com.gamex.rosie.map;

import com.badlogic.gdx.math.Vector3;
import com.gamex.rosie.common.IWorldBody;

public class Map implements IMap {

    private final float tileSize;
    private final IWorldBody[][][] bodyMap;

    public Map(float tileSize, Vector3 mapSize) {

        this.tileSize = tileSize;
        bodyMap = constructMap(mapSize);
    }

    private IWorldBody[][][] constructMap(Vector3 mapSize) {

        int width = (int) mapSize.x;
        int length = (int) mapSize.y;
        int height = (int) mapSize.z;

        return new IWorldBody[width][length][height];
    }

    public CheckResult checkEmptyRelative(Vector3 position, Vector3 relativeOffset) {

        Vector3 absolutePosition = position.add(relativeOffset);

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
}