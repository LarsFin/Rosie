package com.gamex.rosie.controllers;

import com.badlogic.gdx.math.Vector3;
import com.gamex.rosie.common.Factories.Factory;
import com.gamex.rosie.common.IWorldBody;
import com.gamex.rosie.common.Transformation;
import com.gamex.rosie.map.IMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PhysicsControllerTests {

    private PhysicsController subject;

    private IMap mockMap;
    private TransformationFactory mockFactory;

    @Nested
    @DisplayName("Get Gravity Transformations Tests")
    public class GetGravityTransformationsTests {

        @BeforeEach
        public void beforeEach() {

            mockMap = mock(IMap.class);
            mockFactory = mock(TransformationFactory.class);
            Transformation dummyTransformation = mock(Transformation.class);

            when(mockMap.getSize()).thenReturn(new Vector3(2, 2, 2));
            when(mockFactory.build(any(IWorldBody.class), any(Vector3.class))).thenReturn(dummyTransformation);

            subject = new PhysicsController(mockMap, mockFactory);
        }

        @Test
        @DisplayName("Return transformations for multiple World Bodies")
        public void returnTransformationsForWorldBodies() {

            // Arrange
            IWorldBody body1 = mock(IWorldBody.class);
            IWorldBody body2 = mock(IWorldBody.class);

            when(mockMap.getAtAbsolute(Vector3.Zero)).thenReturn(body1);
            when(mockMap.getAtAbsolute(Vector3.X)).thenReturn(body2);

            // Act
            List<Transformation> transformations = subject.getGravityTransformations();

            // Assert
            verify(mockFactory).build(body1, subject.getGravity());
            verify(mockFactory).build(body1, subject.getGravity());

            assertEquals(2, transformations.size());
        }

        @Test
        @DisplayName("Return transformations for World Bodies ordered by x, y then z positions")
        public void returnTransformationsAsOrdered() {

            // Arrange
            IWorldBody body1 = mock(IWorldBody.class);
            IWorldBody body2 = mock(IWorldBody.class);
            IWorldBody body3 = mock(IWorldBody.class);
            IWorldBody body4 = mock(IWorldBody.class);

            when(mockMap.getAtAbsolute(Vector3.Zero)).thenReturn(body1);
            when(mockMap.getAtAbsolute(Vector3.X)).thenReturn(body2);
            when(mockMap.getAtAbsolute(Vector3.Y)).thenReturn(body3);
            when(mockMap.getAtAbsolute(Vector3.Z)).thenReturn(body4);

            Transformation transformation1 = mock(Transformation.class);
            Transformation transformation2 = mock(Transformation.class);
            Transformation transformation3 = mock(Transformation.class);
            Transformation transformation4 = mock(Transformation.class);

            when(mockFactory.build(body1, subject.getGravity())).thenReturn(transformation1);
            when(mockFactory.build(body2, subject.getGravity())).thenReturn(transformation2);
            when(mockFactory.build(body3, subject.getGravity())).thenReturn(transformation3);
            when(mockFactory.build(body4, subject.getGravity())).thenReturn(transformation4);

            // Act
            List<Transformation> transformations = subject.getGravityTransformations();

            // Assert
            verify(mockFactory).build(body1, subject.getGravity());
            verify(mockFactory).build(body2, subject.getGravity());
            verify(mockFactory).build(body3, subject.getGravity());
            verify(mockFactory).build(body4, subject.getGravity());

            assertEquals(transformation1, transformations.get(0));
            assertEquals(transformation2, transformations.get(1));
            assertEquals(transformation3, transformations.get(2));
            assertEquals(transformation4, transformations.get(3));
        }

        @Test
        @DisplayName("Not return transformations for World Bodies which are static")
        public void notReturnTransformationsForStaticWorldBodies() {

            // Arrange
            IWorldBody mockBody = mock(IWorldBody.class);
            when(mockBody.isStatic()).thenReturn(true);

            when(mockMap.getAtAbsolute(Vector3.Zero)).thenReturn(mockBody);

            // Act
            List<Transformation> transformations = subject.getGravityTransformations();

            // Assert
            verify(mockFactory, times(0)).build(any(IWorldBody.class), any(Vector3.class));
            assertTrue(transformations.isEmpty());
        }

        @Test
        @DisplayName("Not return multiple transformations for the same multi coordinate World Body")
        public void notReturnMultipleTransformationsForSameWorldBody() {

            // Arrange
            IWorldBody mockBody = mock(IWorldBody.class);

            when(mockMap.getAtAbsolute(Vector3.Zero)).thenReturn(mockBody);
            when(mockMap.getAtAbsolute(Vector3.X)).thenReturn(mockBody);

            // Act
            List<Transformation> transformations = subject.getGravityTransformations();

            // Assert
            verify(mockFactory).build(any(IWorldBody.class), any(Vector3.class));
            assertEquals(1, transformations.size());
        }
    }

    private interface TransformationFactory extends Factory<Transformation, IWorldBody, Vector3> {

        Transformation build(IWorldBody worldBody, Vector3 displacement);
    }
}
