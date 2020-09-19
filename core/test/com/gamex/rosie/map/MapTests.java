package com.gamex.rosie.map;

import com.badlogic.gdx.math.Vector3;
import com.gamex.rosie.common.IWorldBody;
import com.gamex.rosie.math.Vectors;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class MapTests {

    private Map subject;

    @BeforeEach
    public void beforeEach() {

        float tileSize = 16;
        Vector3 v3 = new Vector3(3, 3, 3);
        subject = new Map(tileSize, v3);
    }

    @Nested
    @DisplayName("Tests for checking relative and absolute positions")
    public class CheckEmptyTests {

        @Test
        @DisplayName("Relative from base position, determine space is empty")
        public void relativeCheckEmpty() {

            // Arrange
            Vector3 pos = new Vector3(1, 1, 1);
            Vector3 relOffset = new Vector3(0, 0, -1);

            // Act
            CheckResult result = subject.checkEmptyRelative(pos, relOffset);

            // Assert
            assertEquals(CheckResult.EMPTY, result);
        }

        @Test
        @DisplayName("Relative from base position, determine space is not empty")
        public void relativeCheckOccupied() {

            // Arrange
            Vector3 pos = new Vector3(1, 1, 1);
            Vector3 relOffset = new Vector3(0, 0, -1);

            IWorldBody mockBody = mock(IWorldBody.class);
            Vector3 instantPos = new Vector3(1, 1, 0);
            subject.putAtAbsolute(mockBody, instantPos);

            // Act
            CheckResult result = subject.checkEmptyRelative(pos, relOffset);

            // Assert
            assertEquals(CheckResult.OCCUPIED, result);
        }

        @Test
        @DisplayName("Checking a location outside map returns out of bounds check")
        public void relativeOutOfBoundsCheck()
        {

            // Arrange
            Vector3 pos = new Vector3(1, 1, 1);
            Vector3 relOffset = new Vector3(0, 0, -2);

            // Act
            CheckResult result = subject.checkEmptyRelative(pos, relOffset);

            // Assert
            assertEquals(CheckResult.OUT_OF_BOUNDS, result);
        }
    }

    @Nested
    @DisplayName("Tests for putting a world body in an absolute position")
    public class PutAtAbsoluteTests {

        @Test
        @DisplayName("Ensure world body is put at absolute position")
        public void isPutAtAbsolutePosition() {

            // Arrange
            Vector3 position = new Vector3(1, 1, 1);
            IWorldBody mockBody = mock(IWorldBody.class);

            // Assert
            assertNull(subject.getAtAbsolute(position));

            // Act
            subject.putAtAbsolute(mockBody, position);

            // Assert
            verify(mockBody).setWorldPosition(position);
            assertEquals(mockBody, subject.getAtAbsolute(position));
        }
    }

    @Nested
    @DisplayName("Tests for putting a world body at a relative position on the map")
    public class putAtRelativePositionTests {

        @Test
        @DisplayName("World body is put at position relative to its own")
        public void isPutAtRelativePosition() {

            // Arrange
            Vector3 relativePosition = new Vector3(0, -1, 0);
            Vector3 currentPosition = new Vector3(1, 1, 1);
            Vector3 expected = Vectors.add(currentPosition, relativePosition);
            IWorldBody mockBody = mock(IWorldBody.class);
            doReturn(currentPosition).when(mockBody).getPosition();

            // Act
            subject.putAtRelative(mockBody, relativePosition);

            // Assert
            verify(mockBody).setWorldPosition(expected);
            assertEquals(mockBody, subject.getAtAbsolute(expected));
        }
    }
}