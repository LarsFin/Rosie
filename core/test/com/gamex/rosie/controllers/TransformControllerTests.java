package com.gamex.rosie.controllers;

import com.badlogic.gdx.math.Vector3;
import com.gamex.rosie.common.IWorldBody;
import com.gamex.rosie.common.Transformation;
import com.gamex.rosie.map.IMap;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

public class TransformControllerTests {

    private TransformController subject;

    private IMap mockMap;

    @BeforeEach
    public void beforeEach() {

        mockMap = mock(IMap.class);

        subject = new TransformController(mockMap);
    }

    @Nested
    @DisplayName("Apply Transform Tests")
    public class MakeMovementTests {

        private Vector3 displacement;

        private Transformation mockTransformation;
        private IWorldBody mockBody1;
        private IWorldBody mockBody2;

        @BeforeEach
        public void beforeEach() {

            displacement = new Vector3(-1, 0, 0);

            mockTransformation = mock(Transformation.class);

            mockBody1 = mock(IWorldBody.class);
            mockBody2 = mock(IWorldBody.class);

            when(mockTransformation.getWorldBody()).thenReturn(mockBody1);
            when(mockTransformation.getDisplacement()).thenReturn(displacement);

            when(mockBody1.isTransformationSafe(displacement)).thenReturn(true);
            when(mockBody2.isTransformationSafe(displacement)).thenReturn(true);
        }

        @Test
        @DisplayName("Move single world body")
        public void moveSingleWorldBody() {

            // Arrange
            when(mockBody1.getObstacles(displacement)).thenReturn(new ArrayList<>());

            // Act
            subject.applyTransform(mockTransformation, Transformation.allConsiderations());

            // Assert
            verify(mockMap).putAtRelative(mockBody1, displacement);
        }

        @Test
        @DisplayName("Move two world bodies with same weight")
        public void moveTwoWorldBodies() {

            // Arrange
            ArrayList<IWorldBody> obstacles = new ArrayList<>();
            obstacles.add(mockBody2);
            when(mockBody1.getObstacles(displacement)).thenReturn(obstacles);

            int wt = 5;
            doReturn(wt).when(mockBody1).getWeight();
            doReturn(wt).when(mockBody2).getWeight();

            when(mockBody2.getObstacles(displacement)).thenReturn(new ArrayList<>());

            // Act
            subject.applyTransform(mockTransformation, Transformation.allConsiderations());

            // Assert
            verify(mockMap).putAtRelative(mockBody1, displacement);
            verify(mockMap).putAtRelative(mockBody2, displacement);
        }

        @Test
        @DisplayName("Move two recursively connected world bodies")
        public void moveTwoRecursivelyConnectedWorldBodies() {

            // Arrange
            ArrayList<IWorldBody> obstacles = new ArrayList<>();
            obstacles.add(mockBody2);
            when(mockBody1.getObstacles(displacement)).thenReturn(obstacles);

            int wt = 5;
            when(mockBody1.getWeight()).thenReturn(wt);
            when(mockBody2.getWeight()).thenReturn(wt);

            ArrayList<IWorldBody> obstacles2 = new ArrayList<>();
            obstacles2.add(mockBody1);
            when(mockBody2.getObstacles(displacement)).thenReturn(obstacles2);

            // Act
            subject.applyTransform(mockTransformation, Transformation.allConsiderations());

            // Assert
            verify(mockMap).putAtRelative(mockBody1, displacement);
            verify(mockMap).putAtRelative(mockBody2, displacement);
        }

        @Test
        @DisplayName("Move two world bodies when second is lighter")
        public void moveTwoWorldBodiesWithDecreasingWeight() {

            // Arrange
            ArrayList<IWorldBody> obstacles = new ArrayList<>();
            obstacles.add(mockBody2);
            when(mockBody1.getObstacles(displacement)).thenReturn(obstacles);

            when(mockBody1.getWeight()).thenReturn(5);
            when(mockBody2.getWeight()).thenReturn(2);

            when(mockBody2.getObstacles(displacement)).thenReturn(new ArrayList<>());

            // Act
            subject.applyTransform(mockTransformation, Transformation.allConsiderations());

            // Assert
            verify(mockMap).putAtRelative(mockBody1, displacement);
            verify(mockMap).putAtRelative(mockBody2, displacement);
        }

        @Test
        @DisplayName("Move world body which is blocked by two other same weight world bodies")
        public void moveBranchedWorldBodyConnections() {

            // Arrange
            IWorldBody mockBody2b = mock(IWorldBody.class);
            IWorldBody mockBody3b = mock(IWorldBody.class);

            when(mockBody2b.isTransformationSafe(displacement)).thenReturn(true);
            when(mockBody3b.isTransformationSafe(displacement)).thenReturn(true);

            ArrayList<IWorldBody> obstacles = new ArrayList<>();
            obstacles.add(mockBody2);
            obstacles.add(mockBody2b);
            when(mockBody1.getObstacles(displacement)).thenReturn(obstacles);

            ArrayList<IWorldBody> obstacles2 = new ArrayList<>();
            obstacles2.add(mockBody3b);
            when(mockBody2b.getObstacles(displacement)).thenReturn(obstacles2);

            int wt = 5;
            when(mockBody1.getWeight()).thenReturn(wt);
            when(mockBody2.getWeight()).thenReturn(wt);
            when(mockBody2b.getWeight()).thenReturn(wt);
            when(mockBody3b.getWeight()).thenReturn(wt);

            // Act
            subject.applyTransform(mockTransformation, Transformation.allConsiderations());

            // Assert
            verify(mockMap).putAtRelative(mockBody1, displacement);
            verify(mockMap).putAtRelative(mockBody2, displacement);
            verify(mockMap).putAtRelative(mockBody2b, displacement);
            verify(mockMap).putAtRelative(mockBody3b, displacement);
        }

        @Test
        @DisplayName("Not move world body over bounds of map")
        public void notMoveWorldBodyOverBoundsOfMap() {

            // Arrange
            when(mockBody1.isTransformationSafe(displacement)).thenReturn(false);

            // Act
            subject.applyTransform(mockTransformation, Transformation.allConsiderations());

            // Assert
            verify(mockMap, times(0)).putAtRelative(any(IWorldBody.class), any(Vector3.class));
        }

        @Test
        @DisplayName("Not move two world bodies when second is heavier")
        public void notMoveTwoWorldBodiesWithIncreasingWeight() {

            // Arrange
            ArrayList<IWorldBody> obstacles = new ArrayList<>();
            obstacles.add(mockBody2);
            when(mockBody1.getObstacles(displacement)).thenReturn(obstacles);

            when(mockBody1.getWeight()).thenReturn(2);
            when(mockBody2.getWeight()).thenReturn(5);

            // Act
            subject.applyTransform(mockTransformation, Transformation.allConsiderations());

            // Assert
            verify(mockMap, times(0)).putAtRelative(any(IWorldBody.class), any(Vector3.class));
        }

        @Test
        @DisplayName("Not move two world bodies when second is static")
        public void notMoveTwoWorldBodiesWhenSecondIsStatic() {

            // Arrange
            ArrayList<IWorldBody> obstacles = new ArrayList<>();
            obstacles.add(mockBody2);
            when(mockBody1.getObstacles(displacement)).thenReturn(obstacles);

            int wt = 5;
            when(mockBody1.getWeight()).thenReturn(wt);
            when(mockBody2.getWeight()).thenReturn(wt);

            when(mockBody2.isStatic()).thenReturn(true);

            // Act
            subject.applyTransform(mockTransformation, Transformation.allConsiderations());

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

            when(mockBody2b.isTransformationSafe(displacement)).thenReturn(true);
            when(mockBody3.isTransformationSafe(displacement)).thenReturn(true);

            ArrayList<IWorldBody> obstacles = new ArrayList<>();
            obstacles.add(mockBody2);
            obstacles.add(mockBody2b);
            when(mockBody1.getObstacles(displacement)).thenReturn(obstacles);

            ArrayList<IWorldBody> obstacles2 = new ArrayList<>();
            obstacles2.add(mockBody3);
            when(mockBody2.getObstacles(displacement)).thenReturn(obstacles2);

            int wt = 5;
            when(mockBody1.getWeight()).thenReturn(wt);
            when(mockBody2.getWeight()).thenReturn(wt);
            when(mockBody2b.getWeight()).thenReturn(wt);
            when(mockBody3.getWeight()).thenReturn(wt);

            when(mockBody3.isStatic()).thenReturn(true);

            // Act
            subject.applyTransform(mockTransformation, Transformation.allConsiderations());

            // Assert
            verify(mockMap, times(0)).putAtRelative(any(IWorldBody.class), any(Vector3.class));
        }
    }
}
