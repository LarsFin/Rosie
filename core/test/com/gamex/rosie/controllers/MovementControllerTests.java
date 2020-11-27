package com.gamex.rosie.controllers;

import com.badlogic.gdx.math.Vector3;
import com.gamex.rosie.common.IWorldBody;
import com.gamex.rosie.map.IMap;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

public class MovementControllerTests {

    private MovementController subject;

    private IMap mockMap;

    @BeforeEach
    public void beforeEach() {

        mockMap = mock(IMap.class);

        subject = new MovementController(mockMap);
    }

    @Nested
    @DisplayName("Make Movement Tests")
    public class MakeMovementTests {

        private Vector3 transformation;

        private IWorldBody mockBody1;
        private IWorldBody mockBody2;

        @BeforeEach
        public void beforeEach() {

            transformation = new Vector3(-1, 0, 0);

            mockBody1 = mock(IWorldBody.class);
            mockBody2 = mock(IWorldBody.class);
        }

        @Test
        @DisplayName("Move single world body")
        public void moveSingleWorldBody() {

            // Arrange
            doReturn(new ArrayList<IWorldBody>()).when(mockBody1.getObstacles(transformation));

            // Act
            subject.makeMovement(mockBody1, transformation);

            // Assert
            verify(mockMap).putAtRelative(mockBody1, transformation);
        }

        @Test
        @DisplayName("Move two world bodies with same weight")
        public void moveTwoWorldBodies() {

            // Arrange
            ArrayList<IWorldBody> obstacles = new ArrayList<>();
            obstacles.add(mockBody2);
            doReturn(obstacles).when(mockBody1).getObstacles(transformation);

            int wt = 5;
            doReturn(wt).when(mockBody1).getWeight();
            doReturn(wt).when(mockBody2).getWeight();

            doReturn(new ArrayList<>()).when(mockBody2).getObstacles(transformation);

            // Act
            subject.makeMovement(mockBody1, transformation);

            // Assert
            verify(mockMap).putAtRelative(mockBody1, transformation);
            verify(mockMap).putAtRelative(mockBody2, transformation);
        }

        @Test
        @DisplayName("Move two recursively connected world bodies")
        public void moveTwoRecursivelyConnectedWorldBodies() {

            // Arrange
            ArrayList<IWorldBody> obstacles = new ArrayList<>();
            obstacles.add(mockBody2);
            doReturn(obstacles).when(mockBody1).getObstacles(transformation);

            int wt = 5;
            doReturn(wt).when(mockBody1).getWeight();
            doReturn(wt).when(mockBody2).getWeight();

            ArrayList<IWorldBody> obstacles2 = new ArrayList<>();
            obstacles2.add(mockBody1);
            doReturn(obstacles2).when(mockBody2).getObstacles(transformation);

            // Act
            subject.makeMovement(mockBody1, transformation);

            // Assert
            verify(mockMap).putAtRelative(mockBody1, transformation);
            verify(mockMap).putAtRelative(mockBody2, transformation);
        }

        @Test
        @DisplayName("Move two world bodies when second is lighter")
        public void moveTwoWorldBodiesWithDecreasingWeight() {

            // Arrange
            ArrayList<IWorldBody> obstacles = new ArrayList<>();
            obstacles.add(mockBody2);
            doReturn(obstacles).when(mockBody1).getObstacles(transformation);

            doReturn(5).when(mockBody1).getWeight();
            doReturn(2).when(mockBody2).getWeight();

            doReturn(new ArrayList<>()).when(mockBody2).getObstacles(transformation);

            // Act
            subject.makeMovement(mockBody1, transformation);

            // Assert
            verify(mockMap).putAtRelative(mockBody1, transformation);
            verify(mockMap).putAtRelative(mockBody2, transformation);
        }

        @Test
        @DisplayName("Move world body which is blocked by two other same weight world bodies")
        public void moveBranchedWorldBodyConnections() {

            // TODO: Finish Test

            // Arrange
            IWorldBody mockBody2b = mock(IWorldBody.class);
            IWorldBody mockBody3b = mock(IWorldBody.class);

            ArrayList<IWorldBody> obstacles = new ArrayList<>();
            obstacles.add(mockBody2);
            obstacles.add(mockBody2b);
            obstacles.add(mockBody3b);
            doReturn(obstacles).when(mockBody1).getObstacles(transformation);

            int wt = 5;
            doReturn(wt).when(mockBody1).getWeight();
            doReturn(wt).when(mockBody2).getWeight();
            doReturn(wt).when(mockBody2b).getWeight();
            doReturn(wt).when(mockBody3b).getWeight();

            // Act
            subject.makeMovement(mockBody1, transformation);

            // Assert
        }

        @Test
        @DisplayName("Not move two world bodies when second is heavier")
        public void notMoveTwoWorldBodiesWithIncreasingWeight() {

            // Arrange

            // Act

            // Assert
        }

        @Test
        @DisplayName("Not move two world bodies when second is static")
        public void notMoveTwoWorldBodiesWhenSecondIsStatic() {

            // Arrange

            // Act

            // Assert
        }

        @Test
        @DisplayName("Not move world body which is blocked by two different world bodies, one of which blocked by " +
                "a static world body")
        public void notMoveBranchedWorldBodyConnectionsWhenOnePathIsStatic() {

            // Arrange

            // Act

            // Assert
        }
    }
}
