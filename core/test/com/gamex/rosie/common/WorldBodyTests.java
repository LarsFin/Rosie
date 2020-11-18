package com.gamex.rosie.common;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.gamex.rosie.map.CheckResult;
import com.gamex.rosie.map.IMap;
import org.junit.jupiter.api.*;

import static com.gamex.rosie.common.WorldConstants._2dDirections;
import static com.gamex.rosie.common.WorldConstants._3dDirections;
import static org.mockito.Mockito.*;

public class WorldBodyTests {

    private WorldBody subject;

    private IMap mockMap;

    @BeforeEach
    public void beforeEach() {

        mockMap = mock(IMap.class);
        Vector3[] startPosition = { Vector3.Zero };

        subject = new WorldBody(mockMap, startPosition);
    }

    @Nested
    @DisplayName("Tests for updating gravitational force on a world body")
    public class GravityUpdateTests {

        @Test
        @DisplayName("World body should fall till space beneath is occupied")
        public void fallUntilGrounded() {

            // Arrange
            Vector3 basePosition = Vector3.Zero;
            Vector3 offset = _3dDirections.get(WorldConstants._3dDirection.DOWN);

            when(mockMap.checkEmptyRelative(basePosition, offset)).thenReturn(CheckResult.EMPTY)
                    .thenReturn(CheckResult.EMPTY)
                    .thenReturn(CheckResult.OCCUPIED);

            // Act
            for (int i = 0; i < 3; i++) {

                subject.gravityUpdate();
            }

            // Assert
            Vector3 expected3dDirection = _3dDirections.get(WorldConstants._3dDirection.DOWN);
            verify(mockMap, times(2)).putAtRelative(subject, expected3dDirection);
        }
    }

    @Nested
    @DisplayName("Tests for the movement of a world body")
    public class MoveTests {

        @Test
        @DisplayName("World body should move to relative position when empty")
        public void moveToRelativePositionWhenEmpty() {

            // Arrange
            Vector3 basePosition = Vector3.Zero;
            WorldConstants._2dDirection direction = WorldConstants._2dDirection.RIGHT;
            Vector2 movement = _2dDirections.get(direction);
            when(mockMap.checkEmptyRelative(basePosition, movement)).thenReturn(CheckResult.EMPTY);

            // Act
            subject.move(direction);

            // Assert
            verify(mockMap).putAtRelative(subject, movement);
        }

        @Test
        @DisplayName("World body should not move to relative position when occupied")
        public void notMoveToRelativePositionWhenOccupied() {

            // Arrange
            Vector3 basePosition = Vector3.Zero;
            WorldConstants._2dDirection direction = WorldConstants._2dDirection.RIGHT;
            Vector2 movement = _2dDirections.get(direction);
            when(mockMap.checkEmptyRelative(basePosition, movement)).thenReturn(CheckResult.OCCUPIED);

            // Act
            subject.move(direction);

            // Assert
            verify(mockMap, times(0)).putAtRelative(subject, movement);
        }

        @Test
        @DisplayName("World body should not move to relative position when out of bounds")
        public void notMoveToRelativePositionWhenOutOfBounds() {


        }
    }
}
