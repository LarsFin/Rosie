package com.gamex.rosie.map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.gamex.rosie.common.IWorldBody;
import com.gamex.rosie.logging.ILogger;
import com.gamex.rosie.logging.LogLevel;
import com.gamex.rosie.math.Vectors;

import static com.gamex.rosie.logging.Messages.Map.Error;

public class Map implements IMap {

    private final float tileSize;
    private final Vector3 size;
    private final IWorldBody[][][] bodyMap;
    private final ILogger logger;

    public Map(float tileSize, Vector3 size, ILogger logger) {

        this.tileSize = tileSize;
        this.size = size;
        this.logger = logger;

        int width = (int) size.x;
        int length = (int) size.y;
        int height = (int) size.z;

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

    public Vector3 getSize() {

        return size;
    }

    public void putAtAbsolute(IWorldBody worldBody, Vector3[] positions) {

        Vector3[] currentPositions = worldBody.getWorldPosition();

        if (currentPositions.length != positions.length) {

            logger.Log(LogLevel.ERROR, Error.InvalidAbsolutePositionSet(currentPositions.length, positions.length));
            return;
        }

        for (Vector3 position : currentPositions) {

            int x = (int) position.x;
            int y = (int) position.y;
            int z = (int) position.z;

            bodyMap[x][y][z] = null;
        }

        for (Vector3 position : positions) {

            int x = (int) position.x;
            int y = (int) position.y;
            int z = (int) position.z;

            bodyMap[x][y][z] = worldBody;
        }

        worldBody.setWorldPosition(positions);
    }

    public void putAtRelative(IWorldBody worldBody, Vector2 relativePosition) {

        Vector3 relativePosition3 = new Vector3(relativePosition.x, relativePosition.y, 0);
        putAtRelative(worldBody, relativePosition3);
    }

    public void putAtRelative(IWorldBody worldBody, Vector3 relativePosition) {

        Vector3[] positions = worldBody.getWorldPosition();

        Vector3[] absolutePositions = new Vector3[positions.length];

        for (int i = 0; i < absolutePositions.length; i++) {

            absolutePositions[i] = Vectors.add(positions[i], relativePosition);
        }

        putAtAbsolute(worldBody, absolutePositions);
    }

    private boolean isAbsoluteInBounds(Vector3 absolutePosition) {

        int x = (int) absolutePosition.x;
        int y = (int) absolutePosition.y;
        int z = (int) absolutePosition.z;

        return x >= 0 && x < size.x &&
                y >= 0 && y < size.y &&
                z >= 0 && z < size.z;
    }
}