package com.gamex.rosie.common;

import com.badlogic.gdx.math.Vector3;
import com.gamex.rosie.map.CheckResult;
import com.gamex.rosie.map.IMap;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WorldBodyTests {

    private WorldBody subject;

    private IMap mockMap;

    @BeforeEach
    public void beforeEach() {

        mockMap = mock(IMap.class);
        Vector3[] startPositions = { Vector3.Zero, Vector3.X, Vector3.Z };

        WorldBodyConfig config = new WorldBodyConfig();
        config.Static = false;
        config.Weight = 1;

        subject = new WorldBody(mockMap, startPositions, config);
    }

    @Nested
    @DisplayName("Tests for constructing WorldBody and its relevant getters and setters")
    public class ConstructorTests {

        @Test
        @DisplayName("Constructor sets positions of WorldBody")
        public void setWorldPosition() {

            // Arrange
            Vector3[] positions = { Vector3.Zero, Vector3.X, Vector3.Y };

            // Act
            subject = new WorldBody(mockMap, positions, new WorldBodyConfig());

            // Assert
            assertEquals(positions, subject.getWorldPosition());
        }

        @Test
        @DisplayName("Constructor sets whether static")
        public void setIsStatic() {

            // Arrange
            WorldBodyConfig config = new WorldBodyConfig();
            config.Static = true;

            // Act
            subject = new WorldBody(mockMap, new Vector3[0], config);

            // Assert
            assertTrue(subject.isStatic());
        }

        @Test
        @DisplayName("Constructor sets weight")
        public void setWeight() {

            // Arrange
            WorldBodyConfig config = new WorldBodyConfig();
            config.Weight = 5;

            // Act
            subject = new WorldBody(mockMap, new Vector3[0], config);

            // Assert
            assertEquals(5, subject.getWeight());
        }
    }

    @Nested
    @DisplayName("Tests for updating gravitational force on a world body")
    public class GravityUpdateTests {

        @Test
        @DisplayName("World body should fall till space beneath is occupied")
        public void fallUntilGrounded() {

            // Arrange
            Vector3[] positions = subject.getWorldPosition();
            Vector3 offset = WorldPhysics.getNormalGravity();

            when(mockMap.checkEmptyRelative(positions[0], offset)).thenReturn(CheckResult.EMPTY)
                    .thenReturn(CheckResult.EMPTY)
                    .thenReturn(CheckResult.OCCUPIED);

            when(mockMap.checkEmptyRelative(positions[1], offset)).thenReturn(CheckResult.EMPTY);

            when(mockMap.checkEmptyRelative(positions[2], offset)).thenReturn(CheckResult.OCCUPIED);

            // Act
            for (int i = 0; i < 3; i++) {

                subject.gravityUpdate();
            }

            // Assert
            verify(mockMap, times(2)).putAtRelative(subject, offset);
        }
    }

    @Nested
    @DisplayName("Is Transformation Safe Tests")
    public class IsTransformationSafeTests {

        @Test
        @DisplayName("Return true when transformation is within bounds of map")
        public void returnTrueWhenSafe() {

            // Arrange
            Vector3 transformation = Vector3.Z;

            when(mockMap.checkEmptyRelative(Vector3.Zero, transformation)).thenReturn(CheckResult.OCCUPIED);
            when(mockMap.checkEmptyRelative(Vector3.X, transformation)).thenReturn(CheckResult.EMPTY);
            when(mockMap.checkEmptyRelative(Vector3.Z, transformation)).thenReturn(CheckResult.EMPTY);

            // Act
            boolean result = subject.isTransformationSafe(transformation);

            // Assert
            assertTrue(result);
        }

        @Test
        @DisplayName("Return false when transformation is out of bounds of map")
        public void returnFalseWhenUnsafe() {

            // Arrange
            Vector3 transformation = Vector3.Z;

            when(mockMap.checkEmptyRelative(Vector3.Zero, transformation)).thenReturn(CheckResult.EMPTY);
            when(mockMap.checkEmptyRelative(Vector3.X, transformation)).thenReturn(CheckResult.OUT_OF_BOUNDS);
            when(mockMap.checkEmptyRelative(Vector3.Z, transformation)).thenReturn(CheckResult.OCCUPIED);

            // Act
            boolean result = subject.isTransformationSafe(transformation);

            // Assert
            assertFalse(result);
        }
    }
}
