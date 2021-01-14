package com.gamex.rosie.controllers;

import com.badlogic.gdx.math.Vector3;
import com.gamex.rosie.common.Factories.*;
import com.gamex.rosie.common.IWorldBody;
import com.gamex.rosie.common.Transformation;
import com.gamex.rosie.common.TransformationResult;
import com.gamex.rosie.map.IMap;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TransformControllerTests {

    private TransformController subject;

    private IMap mockMap;
    private TransformationResultFactory mockTransformationResultFactory;
    private TransformationFactory mockTransformationFactory;
    private ChainedTransformationFactory mockChainedTransformationFactory;

    @BeforeEach
    public void beforeEach() {

        mockMap = mock(IMap.class);
        mockTransformationResultFactory = mock(TransformationResultFactory.class);
        mockTransformationFactory = mock(TransformationFactory.class);
        mockChainedTransformationFactory = mock(ChainedTransformationFactory.class);

        subject = new TransformController(mockMap,
                mockTransformationResultFactory,
                mockTransformationFactory,
                mockChainedTransformationFactory);
    }

    @Nested
    @DisplayName("Apply Transform Tests")
    public class MakeMovementTests {

        private Vector3 displacement;

        private Transformation mockTransformation;
        private Transformation mockAttemptedTransformation;
        private TransformationResult mockTransformationResult;
        private IWorldBody mockBody1;
        private IWorldBody mockBody2;

        @BeforeEach
        public void beforeEach() {

            displacement = new Vector3(-1, 0, 0);

            mockTransformation = mock(Transformation.class);
            mockAttemptedTransformation = mock(Transformation.class);
            mockTransformationResult = mock(TransformationResult.class);

            mockBody1 = mock(IWorldBody.class);
            mockBody2 = mock(IWorldBody.class);

            when(mockTransformationFactory.build(mockBody1, displacement)).thenReturn(mockAttemptedTransformation);

            when(mockTransformation.getReactingWorldBody()).thenReturn(mockBody1);
            when(mockTransformation.getDisplacement()).thenReturn(displacement);

            when(mockBody1.isTransformationSafe(displacement)).thenReturn(true);
            when(mockBody2.isTransformationSafe(displacement)).thenReturn(true);
        }

        @Test
        @DisplayName("Move single world body and return respective result")
        public void moveSingleWorldBodyAndReturnResult() {

            // Arrange
            when(mockBody1.getObstacles(displacement)).thenReturn(new ArrayList<>());

            when(mockTransformationResultFactory.build(eq(true), argThat(list ->
                    list.contains(mockAttemptedTransformation) && list.size() == 1)))
                    .thenReturn(mockTransformationResult);

            // Act
            TransformationResult result = subject.applyTransform(mockTransformation, Transformation.allConsiderations());

            // Assert
            verify(mockMap).putAtRelative(mockBody1, displacement);
            assertEquals(mockTransformationResult, result);
        }

        @Test
        @DisplayName("Move two world bodies with same weight and return respective result")
        public void moveTwoWorldBodiesAndReturnResult() {

            // Arrange
            ArrayList<IWorldBody> obstacles = new ArrayList<>();
            obstacles.add(mockBody2);
            when(mockBody1.getObstacles(displacement)).thenReturn(obstacles);

            int wt = 5;
            doReturn(wt).when(mockBody1).getWeight();
            doReturn(wt).when(mockBody2).getWeight();

            when(mockBody2.getObstacles(displacement)).thenReturn(new ArrayList<>());

            Transformation mockAttemptedTransformation2 = mock(Transformation.class);
            when(mockChainedTransformationFactory.build(mockBody1, mockBody2, displacement))
                    .thenReturn(mockAttemptedTransformation2);

            when(mockTransformationResultFactory.build(eq(true), argThat(list ->
                    list.contains(mockAttemptedTransformation) &&
                            list.contains(mockAttemptedTransformation2) && list.size() == 2)))
                    .thenReturn(mockTransformationResult);

            // Act
            TransformationResult result = subject.applyTransform(mockTransformation, Transformation.allConsiderations());

            // Assert
            verify(mockMap).putAtRelative(mockBody1, displacement);
            verify(mockMap).putAtRelative(mockBody2, displacement);
            assertEquals(mockTransformationResult, result);
        }

        @Test
        @DisplayName("Move two recursively connected world bodies and return respective result")
        public void moveTwoRecursivelyConnectedWorldBodiesAndReturnResult() {

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

            Transformation mockAttemptedTransformation2 = mock(Transformation.class);
            when(mockChainedTransformationFactory.build(mockBody1, mockBody2, displacement))
                    .thenReturn(mockAttemptedTransformation2);

            when(mockTransformationResultFactory.build(eq(true), argThat(list ->
                    list.contains(mockAttemptedTransformation) &&
                            list.contains(mockAttemptedTransformation2) && list.size() == 2)))
                    .thenReturn(mockTransformationResult);

            // Act
            TransformationResult result = subject.applyTransform(mockTransformation, Transformation.allConsiderations());

            // Assert
            verify(mockMap).putAtRelative(mockBody1, displacement);
            verify(mockMap).putAtRelative(mockBody2, displacement);
            assertEquals(mockTransformationResult, result);
        }

        @Test
        @DisplayName("Move two world bodies when second is lighter and return respective result")
        public void moveTwoWorldBodiesWithDecreasingWeightAndReturnResult() {

            // Arrange
            ArrayList<IWorldBody> obstacles = new ArrayList<>();
            obstacles.add(mockBody2);
            when(mockBody1.getObstacles(displacement)).thenReturn(obstacles);

            when(mockBody1.getWeight()).thenReturn(5);
            when(mockBody2.getWeight()).thenReturn(2);

            when(mockBody2.getObstacles(displacement)).thenReturn(new ArrayList<>());

            Transformation mockAttemptedTransformation2 = mock(Transformation.class);
            when(mockChainedTransformationFactory.build(mockBody1, mockBody2, displacement))
                    .thenReturn(mockAttemptedTransformation2);

            when(mockTransformationResultFactory.build(eq(true), argThat(list ->
                    list.contains(mockAttemptedTransformation) &&
                            list.contains(mockAttemptedTransformation2) && list.size() == 2)))
                    .thenReturn(mockTransformationResult);

            // Act
            TransformationResult result = subject.applyTransform(mockTransformation, Transformation.allConsiderations());

            // Assert
            verify(mockMap).putAtRelative(mockBody1, displacement);
            verify(mockMap).putAtRelative(mockBody2, displacement);
            assertEquals(mockTransformationResult, result);
        }

        @Test
        @DisplayName("Move world body which is blocked by two other same weight world bodies and return respective result")
        public void moveBranchedWorldBodyConnectionsAndReturnResult() {

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

            Transformation mockAttemptedTransformation2 = mock(Transformation.class);
            when(mockChainedTransformationFactory.build(mockBody1, mockBody2, displacement))
                    .thenReturn(mockAttemptedTransformation2);

            Transformation mockAttemptedTransformation3 = mock(Transformation.class);
            when(mockChainedTransformationFactory.build(mockBody1, mockBody2b, displacement))
                    .thenReturn(mockAttemptedTransformation3);

            Transformation mockAttemptedTransformation4 = mock(Transformation.class);
            when(mockChainedTransformationFactory.build(mockBody2b, mockBody3b, displacement))
                    .thenReturn(mockAttemptedTransformation4);

            when(mockTransformationResultFactory.build(eq(true), argThat(list ->
                    list.contains(mockAttemptedTransformation) &&
                            list.contains(mockAttemptedTransformation2) &&
                            list.contains(mockAttemptedTransformation3) &&
                            list.contains(mockAttemptedTransformation4) && list.size() == 4)))
                    .thenReturn(mockTransformationResult);

            // Act
            TransformationResult result = subject.applyTransform(mockTransformation, Transformation.allConsiderations());

            // Assert
            verify(mockMap).putAtRelative(mockBody1, displacement);
            verify(mockMap).putAtRelative(mockBody2, displacement);
            verify(mockMap).putAtRelative(mockBody2b, displacement);
            verify(mockMap).putAtRelative(mockBody3b, displacement);
            assertEquals(mockTransformationResult, result);
        }

        @Test
        @DisplayName("Move two world bodies with increasing weight when weight is not a consideration and return respective result")
        public void moveTwoWorldBodiesWithIncreasingWeightWhenNotConsideredAndReturnResult() {

            // Arrange
            ArrayList<IWorldBody> obstacles = new ArrayList<>();
            obstacles.add(mockBody2);
            when(mockBody1.getObstacles(displacement)).thenReturn(obstacles);

            when(mockBody1.getWeight()).thenReturn(2);
            when(mockBody2.getWeight()).thenReturn(5);

            Transformation.Consideration[] considerations = { Transformation.Consideration.STATIC };

            Transformation mockAttemptedTransformation2 = mock(Transformation.class);
            when(mockChainedTransformationFactory.build(mockBody1, mockBody2, displacement))
                    .thenReturn(mockAttemptedTransformation2);

            when(mockTransformationResultFactory.build(eq(true), argThat(list ->
                    list.contains(mockAttemptedTransformation) &&
                            list.contains(mockAttemptedTransformation2) && list.size() == 2)))
                    .thenReturn(mockTransformationResult);

            // Act
            TransformationResult result = subject.applyTransform(mockTransformation, considerations);

            // Assert
            verify(mockMap).putAtRelative(mockBody1, displacement);
            verify(mockMap).putAtRelative(mockBody2, displacement);
            assertEquals(mockTransformationResult, result);
        }

        @Test
        @DisplayName("Move world body which is static when static is not a consideration and return respective result")
        public void moveStaticWorldBodyWhenNotConsideredAndReturnResult() {

            // Arrange
            when(mockBody1.isStatic()).thenReturn(true);

            Transformation.Consideration[] considerations = { Transformation.Consideration.WEIGHT };

            when(mockTransformationResultFactory.build(eq(true), argThat(list ->
                    list.contains(mockAttemptedTransformation) && list.size() == 1)))
                    .thenReturn(mockTransformationResult);

            // Act
            TransformationResult result = subject.applyTransform(mockTransformation, considerations);

            // Assert
            verify(mockMap).putAtRelative(mockBody1, displacement);
            assertEquals(mockTransformationResult, result);
        }

        @Test
        @DisplayName("Move two world bodies, where second is static when static is not a consideration and return respective result")
        public void moveTwoWorldBodiesWhereSecondIsStaticWhenNotConsideredAndReturnResult() {

            // Arrange
            ArrayList<IWorldBody> obstacles = new ArrayList<>();
            obstacles.add(mockBody2);
            when(mockBody1.getObstacles(displacement)).thenReturn(obstacles);

            int wt = 5;
            when(mockBody1.getWeight()).thenReturn(wt);
            when(mockBody2.getWeight()).thenReturn(wt);

            when(mockBody2.isStatic()).thenReturn(true);

            Transformation.Consideration[] considerations = { Transformation.Consideration.WEIGHT };

            Transformation mockAttemptedTransformation2 = mock(Transformation.class);
            when(mockChainedTransformationFactory.build(mockBody1, mockBody2, displacement))
                    .thenReturn(mockAttemptedTransformation2);

            when(mockTransformationResultFactory.build(eq(true), argThat(list ->
                    list.contains(mockAttemptedTransformation) &&
                            list.contains(mockAttemptedTransformation2) && list.size() == 2)))
                    .thenReturn(mockTransformationResult);

            // Act
            TransformationResult result = subject.applyTransform(mockTransformation, considerations);

            // Assert
            verify(mockMap).putAtRelative(mockBody1, displacement);
            verify(mockMap).putAtRelative(mockBody2, displacement);
            assertEquals(mockTransformationResult, result);
        }

        @Test
        @DisplayName("Not move world body over bounds of map and return respective result")
        public void notMoveWorldBodyOverBoundsOfMapAndReturnResult() {

            // Arrange
            when(mockBody1.isTransformationSafe(displacement)).thenReturn(false);

            when(mockTransformationResultFactory.build(eq(false), argThat(list ->
                    list.contains(mockAttemptedTransformation) && list.size() == 1)))
                    .thenReturn(mockTransformationResult);

            // Act
            TransformationResult result = subject.applyTransform(mockTransformation, Transformation.allConsiderations());

            // Assert
            verify(mockMap, times(0)).putAtRelative(any(IWorldBody.class), any(Vector3.class));
            assertEquals(mockTransformationResult, result);
        }

        @Test
        @DisplayName("Not move world body which is static and return respective result")
        public void notMoveWorldBodyWhichIsStaticAndReturnResult() {

            // Arrange
            when(mockBody1.isStatic()).thenReturn(true);

            when(mockTransformationResultFactory.build(eq(false), argThat(list ->
                    list.contains(mockAttemptedTransformation) && list.size() == 1)))
                    .thenReturn(mockTransformationResult);

            // Act
            TransformationResult result = subject.applyTransform(mockTransformation, Transformation.allConsiderations());

            // Assert
            verify(mockMap, times(0)).putAtRelative(any(IWorldBody.class), any(Vector3.class));
            assertEquals(mockTransformationResult, result);
        }

        @Test
        @DisplayName("Not move two world bodies when second is heavier and return respective result")
        public void notMoveTwoWorldBodiesWithIncreasingWeightAndReturnResult() {

            // Arrange
            ArrayList<IWorldBody> obstacles = new ArrayList<>();
            obstacles.add(mockBody2);
            when(mockBody1.getObstacles(displacement)).thenReturn(obstacles);

            when(mockBody1.getWeight()).thenReturn(2);
            when(mockBody2.getWeight()).thenReturn(5);

            Transformation mockAttemptedTransformation2 = mock(Transformation.class);
            when(mockChainedTransformationFactory.build(mockBody1, mockBody2, displacement))
                    .thenReturn(mockAttemptedTransformation2);

            when(mockTransformationResultFactory.build(eq(false), argThat(list ->
                    list.contains(mockAttemptedTransformation) &&
                            list.contains(mockAttemptedTransformation2) && list.size() == 2)))
                    .thenReturn(mockTransformationResult);

            // Act
            TransformationResult result = subject.applyTransform(mockTransformation, Transformation.allConsiderations());

            // Assert
            verify(mockMap, times(0)).putAtRelative(any(IWorldBody.class), any(Vector3.class));
            assertEquals(mockTransformationResult, result);
        }

        @Test
        @DisplayName("Not move two world bodies when second is static and return respective result")
        public void notMoveTwoWorldBodiesWhenSecondIsStaticAndReturnResult() {

            // Arrange
            ArrayList<IWorldBody> obstacles = new ArrayList<>();
            obstacles.add(mockBody2);
            when(mockBody1.getObstacles(displacement)).thenReturn(obstacles);

            int wt = 5;
            when(mockBody1.getWeight()).thenReturn(wt);
            when(mockBody2.getWeight()).thenReturn(wt);

            when(mockBody2.isStatic()).thenReturn(true);

            Transformation mockAttemptedTransformation2 = mock(Transformation.class);
            when(mockChainedTransformationFactory.build(mockBody1, mockBody2, displacement))
                    .thenReturn(mockAttemptedTransformation2);

            when(mockTransformationResultFactory.build(eq(false), argThat(list ->
                    list.contains(mockAttemptedTransformation) &&
                            list.contains(mockAttemptedTransformation2) && list.size() == 2)))
                    .thenReturn(mockTransformationResult);

            // Act
            TransformationResult result = subject.applyTransform(mockTransformation, Transformation.allConsiderations());

            // Assert
            verify(mockMap, times(0)).putAtRelative(any(IWorldBody.class), any(Vector3.class));
            assertEquals(mockTransformationResult, result);
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

            Transformation mockAttemptedTransformation2 = mock(Transformation.class);
            when(mockChainedTransformationFactory.build(mockBody1, mockBody2, displacement))
                    .thenReturn(mockAttemptedTransformation2);

            Transformation mockAttemptedTransformation3 = mock(Transformation.class);
            when(mockChainedTransformationFactory.build(mockBody1, mockBody2b, displacement))
                    .thenReturn(mockAttemptedTransformation3);

            Transformation mockAttemptedTransformation4 = mock(Transformation.class);
            when(mockChainedTransformationFactory.build(mockBody2, mockBody3, displacement))
                    .thenReturn(mockAttemptedTransformation4);

            when(mockTransformationResultFactory.build(eq(false), argThat(list ->
                    list.contains(mockAttemptedTransformation) &&
                            list.contains(mockAttemptedTransformation2) &&
                            list.contains(mockAttemptedTransformation3) &&
                            list.contains(mockAttemptedTransformation4) && list.size() == 2)))
                    .thenReturn(mockTransformationResult);

            // Act
            TransformationResult result =  subject.applyTransform(mockTransformation, Transformation.allConsiderations());

            // Assert
            verify(mockMap, times(0)).putAtRelative(any(IWorldBody.class), any(Vector3.class));
            assertEquals(mockTransformationResult, result);
        }
    }

    private interface TransformationResultFactory extends Factory2<TransformationResult, Boolean, List<Transformation>> {

        TransformationResult build(Boolean successful, List<Transformation> attemptedTransformations);
    }

    private interface TransformationFactory extends Factory2<Transformation, IWorldBody, Vector3> {

        Transformation build(IWorldBody worldBody, Vector3 displacement);
    }

    private interface ChainedTransformationFactory extends Factory3<Transformation, IWorldBody, IWorldBody, Vector3> {

        Transformation build(IWorldBody initiatingWorldBody, IWorldBody reactingWorldBody, Vector3 displacement);
    }
}
