package com.gamex.rosie.controllers;

import com.badlogic.gdx.math.Vector3;
import com.gamex.rosie.common.IWorldBody;
import com.gamex.rosie.map.IMap;
import org.junit.jupiter.api.*;

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
            when(mockBody1.getObstacles(transformation)).thenReturn(new ArrayList<IWorldBody>());

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
            when(mockBody1.getObstacles(transformation)).thenReturn(obstacles);

            int wt = 5;
            doReturn(wt).when(mockBody1).getWeight();
            doReturn(wt).when(mockBody2).getWeight();

            when(mockBody2.getObstacles(transformation)).thenReturn(new ArrayList<IWorldBody>());

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
            when(mockBody1.getObstacles(transformation)).thenReturn(obstacles);

            int wt = 5;
            when(mockBody1.getWeight()).thenReturn(wt);
            when(mockBody2.getWeight()).thenReturn(wt);

            ArrayList<IWorldBody> obstacles2 = new ArrayList<>();
            obstacles2.add(mockBody1);
            when(mockBody2.getObstacles(transformation)).thenReturn(obstacles2);

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
            when(mockBody1.getObstacles(transformation)).thenReturn(obstacles);

            when(mockBody1.getWeight()).thenReturn(5);
            when(mockBody2.getWeight()).thenReturn(2);

            when(mockBody2.getObstacles(transformation)).thenReturn(new ArrayList<IWorldBody>());

            // Act
            subject.makeMovement(mockBody1, transformation);

            // Assert
            verify(mockMap).putAtRelative(mockBody1, transformation);
            verify(mockMap).putAtRelative(mockBody2, transformation);
        }

        @Test
        @DisplayName("Move world body which is blocked by two other same weight world bodies")
        public void moveBranchedWorldBodyConnections() {

            // Arrange
            IWorldBody mockBody2b = mock(IWorldBody.class);
            IWorldBody mockBody3b = mock(IWorldBody.class);

            ArrayList<IWorldBody> obstacles = new ArrayList<>();
            obstacles.add(mockBody2);
            obstacles.add(mockBody2b);
            when(mockBody1.getObstacles(transformation)).thenReturn(obstacles);

            ArrayList<IWorldBody> obstacles2 = new ArrayList<>();
            obstacles2.add(mockBody3b);
            when(mockBody2b.getObstacles(transformation)).thenReturn(obstacles2);

            int wt = 5;
            when(mockBody1.getWeight()).thenReturn(wt);
            when(mockBody2.getWeight()).thenReturn(wt);
            when(mockBody2b.getWeight()).thenReturn(wt);
            when(mockBody3b.getWeight()).thenReturn(wt);

            // Act
            subject.makeMovement(mockBody1, transformation);

            // Assert
            verify(mockMap).putAtRelative(mockBody1, transformation);
            verify(mockMap).putAtRelative(mockBody2, transformation);
            verify(mockMap).putAtRelative(mockBody2b, transformation);
            verify(mockMap).putAtRelative(mockBody3b, transformation);
        }

        @Test
        @DisplayName("Not move two world bodies when second is heavier")
        public void notMoveTwoWorldBodiesWithIncreasingWeight() {

            // Arrange
            ArrayList<IWorldBody> obstacles = new ArrayList<>();
            obstacles.add(mockBody2);
            when(mockBody1.getObstacles(transformation)).thenReturn(obstacles);

            when(mockBody1.getWeight()).thenReturn(2);
            when(mockBody2.getWeight()).thenReturn(5);

            // Act
            subject.makeMovement(mockBody1, transformation);

            // Assert
            verify(mockMap, times(0)).putAtRelative(any(IWorldBody.class), any(Vector3.class));
        }

        @Test
        @DisplayName("Not move two world bodies when second is static")
        public void notMoveTwoWorldBodiesWhenSecondIsStatic() {

            // Arrange
            ArrayList<IWorldBody> obstacles = new ArrayList<>();
            obstacles.add(mockBody2);
            when(mockBody1.getObstacles(transformation)).thenReturn(obstacles);

            int wt = 5;
            when(mockBody1.getWeight()).thenReturn(wt);
            when(mockBody2.getWeight()).thenReturn(wt);

            when(mockBody2.isStatic()).thenReturn(true);

            // Act
            subject.makeMovement(mockBody1, transformation);

            // Assert
            verify(mockMap, times(0)).putAtRelative(any(IWorldBody.class), any(Vector3.class));
        }

        @Test
        @DisplayName("Not move world body which is blocked by two different world bodies, one of which blocked by " +
                "a static world body")
        public void notMoveBranchedWorldBodyConnectionsWhenOnePathIsStatic() {

            // Arrange
            IWorldBody mockBody2b = mock(IWorldBody.class);
            IWorldBody mockBody3 = mock(IWorldBody.class);

            ArrayList<IWorldBody> obstacles = new ArrayList<>();
            obstacles.add(mockBody2);
            obstacles.add(mockBody2b);
            when(mockBody1.getObstacles(transformation)).thenReturn(obstacles);

            ArrayList<IWorldBody> obstacles2 = new ArrayList<>();
            obstacles2.add(mockBody3);
            when(mockBody2.getObstacles(transformation)).thenReturn(obstacles2);

            int wt = 5;
            when(mockBody1.getWeight()).thenReturn(wt);
            when(mockBody2.getWeight()).thenReturn(wt);
            when(mockBody2b.getWeight()).thenReturn(wt);
            when(mockBody3.getWeight()).thenReturn(wt);

            when(mockBody3.isStatic()).thenReturn(true);

            // Act
            subject.makeMovement(mockBody1, transformation);

            // Assert
            verify(mockMap, times(0)).putAtRelative(any(IWorldBody.class), any(Vector3.class));
        }
    }
}
