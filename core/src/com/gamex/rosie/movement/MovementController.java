package com.gamex.rosie.movement;

import com.badlogic.gdx.math.Vector3;
import com.gamex.rosie.common.IWorldBody;
import com.gamex.rosie.map.IMap;

import java.util.ArrayList;

public class MovementController implements IMovementController {

    private ArrayList<ArrayList<IWorldBody>> concernedBodySets;
    private final IMap map;

    public MovementController(IMap map) {

        concernedBodySets = new ArrayList<>();
        this.map = map;
    }

    public void makeMovement(IWorldBody worldBody, Vector3 transformation) {

        // Add initial concerned body
        concernedBodySets.add(new ArrayList<IWorldBody>());
        concernedBodySets.get(0).add(worldBody);

        for (int i = 0; i < concernedBodySets.size(); i++) {

            ArrayList<IWorldBody> concernedBodies = concernedBodySets.get(i);

            for (IWorldBody concernedBody : concernedBodies) {

                ArrayList<IWorldBody> obstacleBodies = concernedBody.getObstacles(transformation);

                for (IWorldBody obstacleBody : obstacleBodies) {

                    if (obstacleBody.isStatic()) {

                        concernedBodySets = new ArrayList<>();
                        return;
                    }

                    // Check obstacle hasn't been a concerned body to avoid infinite recursion
                    for (int j = i - 1; j >= 0; j--) {

                        if (concernedBodySets.get(j).contains(obstacleBody)) {

                            return;
                        }
                    }

                    if (concernedBodySets.size() <= i + 1) {

                        concernedBodySets.add(new ArrayList<IWorldBody>());
                    }

                    concernedBodySets.get(i + 1).add(obstacleBody);
                }
            }
        }

        for (int i = concernedBodySets.size() - 1; i >= 0; i--) {

            ArrayList<IWorldBody> bodiesToMove = concernedBodySets.get(i);

            for (IWorldBody bodyToMove : bodiesToMove) {

                map.putAtRelative(bodyToMove, transformation);
            }
        }

        // Clear concerned body sets
        concernedBodySets = new ArrayList<>();
    }
}