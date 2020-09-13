package com.gamex.rosie.common;

import com.badlogic.gdx.math.Vector3;
import com.gamex.rosie.map.CheckResult;
import com.gamex.rosie.map.IMap;
import org.junit.jupiter.api.*;

import static com.gamex.rosie.common.WorldConstants._3dDirections;
import static org.mockito.Mockito.*;

public class WorldBodyTests {

    private WorldBody subject;

    private IMap mockMap;

    @BeforeEach
    public void beforeEach() {

        mockMap = mock(IMap.class);

        subject = new WorldBody(mockMap);
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
}
