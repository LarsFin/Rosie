package com.gamex.rosie.common;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.gamex.rosie.map.CheckResult;
import com.gamex.rosie.map.IMap;
import org.junit.jupiter.api.*;

import static com.gamex.rosie.common.WorldConstants._2dDirections;
import static org.mockito.Mockito.*;

public class WorldBodyTests {

    private WorldBody subject;

    private IMap mockMap;

    @BeforeEach
    public void beforeEach() {

        mockMap = mock(IMap.class);
        Vector3[] startPositions = { Vector3.Zero, Vector3.X, Vector3.Z };

        subject = new WorldBody(mockMap, startPositions, false);
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
    @DisplayName("Tests for the movement of a world body")
    public class MoveTests {

        @Test
        @DisplayName("World body should move to relative position when empty")
        public void moveToRelativePositionWhenEmpty() {

            // Arrange
            Vector3[] positions = subject.getWorldPosition();
            WorldConstants._2dDirection direction = WorldConstants._2dDirection.RIGHT;
            Vector2 movement = _2dDirections.get(direction);

            when(mockMap.checkEmptyRelative(positions[0], movement)).thenReturn(CheckResult.OCCUPIED);
            when(mockMap.checkEmptyRelative(positions[1], movement)).thenReturn(CheckResult.EMPTY);
            when(mockMap.checkEmptyRelative(positions[2], movement)).thenReturn(CheckResult.EMPTY);

            // Act
            subject.move(direction);

            // Assert
            verify(mockMap).putAtRelative(subject, movement);
        }

        @Test
        @DisplayName("World body should not move to relative position when occupied")
        public void notMoveToRelativePositionWhenOccupied() {

            // Arrange
            Vector3[] positions = subject.getWorldPosition();
            WorldConstants._2dDirection direction = WorldConstants._2dDirection.RIGHT;
            Vector2 movement = _2dDirections.get(direction);

            when(mockMap.checkEmptyRelative(positions[0], movement)).thenReturn(CheckResult.OCCUPIED);
            when(mockMap.checkEmptyRelative(positions[1], movement)).thenReturn(CheckResult.OCCUPIED);
            when(mockMap.checkEmptyRelative(positions[2], movement)).thenReturn(CheckResult.EMPTY);

            // Act
            subject.move(direction);

            // Assert
            verify(mockMap, times(0)).putAtRelative(subject, movement);
        }

        @Test
        @DisplayName("World body should not move to relative position when out of bounds")
        public void notMoveToRelativePositionWhenOutOfBounds() {

            // Arrange
            Vector3[] positions = subject.getWorldPosition();
            WorldConstants._2dDirection direction = WorldConstants._2dDirection.RIGHT;
            Vector2 movement = _2dDirections.get(direction);

            when(mockMap.checkEmptyRelative(positions[0], movement)).thenReturn(CheckResult.EMPTY);
            when(mockMap.checkEmptyRelative(positions[1], movement)).thenReturn(CheckResult.OCCUPIED);
            when(mockMap.checkEmptyRelative(positions[2], movement)).thenReturn(CheckResult.OUT_OF_BOUNDS);

            // Act
            subject.move(direction);

            // Assert
            verify(mockMap, times(0)).putAtRelative(subject, movement);
        }
    }
}
