package com.gamex.rosie.map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.gamex.rosie.common.IWorldBody;
import com.gamex.rosie.math.Vectors;

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

    public CheckResult checkEmptyRelative(Vector3 position, Vector2 relativeOffset) {

        Vector3 absolutePosition = Vectors.add(position, relativeOffset);

        return checkEmptyAbsolute(absolutePosition);
    }

    public CheckResult checkEmptyRelative(Vector3 position, Vector3 relativeOffset) {

        Vector3 absolutePosition = Vectors.add(position, relativeOffset);

        return checkEmptyAbsolute(absolutePosition);
    }

    public CheckResult checkEmptyAbsolute(Vector3 position) {

        if (!isAbsoluteInBounds(position))
            return CheckResult.OUT_OF_BOUNDS;

        if (getAtAbsolute(position) != null)
            return CheckResult.OCCUPIED;

        return CheckResult.EMPTY;
    }

    public IWorldBody getAtAbsolute(Vector3 absolutePosition) {

        int x = (int) absolutePosition.x;
        int y = (int) absolutePosition.y;
        int z = (int) absolutePosition.z;

        return bodyMap[x][y][z];
    }

    public void putAtAbsolute(IWorldBody worldBody, Vector3 position) {

        int x = (int) position.x;
        int y = (int) position.y;
        int z = (int) position.z;

        bodyMap[x][y][z] = worldBody;
        worldBody.setWorldPosition(position);
    }

    public void putAtRelative(IWorldBody worldBody, Vector2 relativePosition) {

        Vector3 position = worldBody.getWorldPosition();

        int x = (int) (position.x + relativePosition.x);
        int y = (int) (position.y + relativePosition.y);
        int z = (int) position.z;

        bodyMap[x][y][z] = worldBody;
        worldBody.setWorldPosition(new Vector3(x, y, z));
    }

    public void putAtRelative(IWorldBody worldBody, Vector3 relativePosition) {

        Vector3 position = worldBody.getWorldPosition();

        int x = (int) (position.x + relativePosition.x);
        int y = (int) (position.y + relativePosition.y);
        int z = (int) (position.z + relativePosition.z);

        bodyMap[x][y][z] = worldBody;
        worldBody.setWorldPosition(new Vector3(x, y, z));
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