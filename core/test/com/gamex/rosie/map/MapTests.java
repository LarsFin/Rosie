package com.gamex.rosie.map;

import com.badlogic.gdx.math.Vector3;
import com.gamex.rosie.common.IWorldBody;
import com.gamex.rosie.logging.ILogger;
import com.gamex.rosie.logging.LogLevel;
import com.gamex.rosie.math.Vectors;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
import static com.gamex.rosie.logging.Messages.Map.Error;

public class MapTests {

    private Map subject;
    private ILogger mockLogger;

    @BeforeEach
    public void beforeEach() {

        float tileSize = 16;
        Vector3 v3 = new Vector3(3, 3, 3);
        mockLogger = mock(ILogger.class);

        subject = new Map(tileSize, v3, mockLogger);
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
            Vector3[] instantPositions = { Vectors.add(pos, relOffset) };
            doReturn(new Vector3[1]).when(mockBody).getWorldPosition();
            subject.putAtAbsolute(mockBody, instantPositions);

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

        IWorldBody mockBody;

        @BeforeEach
        public void beforeEach() {

            mockBody = mock(IWorldBody.class);
        }

        @Test
        @DisplayName("Ensure world body is put at absolute position")
        public void isPutAtAbsolutePosition() {

            // Arrange
            Vector3 position = new Vector3(1, 1, 1);
            Vector3[] positions = { position };
            doReturn(new Vector3[] { Vector3.Zero }).when(mockBody).getWorldPosition();

            // Assert
            assertNull(subject.getAtAbsolute(position));

            // Act
            subject.putAtAbsolute(mockBody, positions);

            // Assert
            verify(mockBody).setWorldPosition(positions);
            assertEquals(mockBody, subject.getAtAbsolute(position));
        }

        @Test
        @DisplayName("When absolute positions count is different to world body's positions; log error and prevent movement")
        public void logErrorWhenAbsolutePositionsAreInvalid() {

            // Arrange
            Vector3[] currentPositions = new Vector3[0];
            Vector3[] absolutePositions = { Vector3.Zero };
            doReturn(currentPositions).when(mockBody).getWorldPosition();

            // Act
            subject.putAtAbsolute(mockBody, absolutePositions);

            // Assert
            verify(mockLogger).Log(LogLevel.ERROR, Error.InvalidAbsolutePositionSet(0, 1));
            verify(mockBody, times(0)).setWorldPosition(absolutePositions);
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
            Vector3[] currentPosition = { new Vector3(1, 1, 1) };
            Vector3[] expected = { new Vector3(1, 0, 1) };
            IWorldBody mockBody = mock(IWorldBody.class);
            doReturn(currentPosition).when(mockBody).getWorldPosition();

            // Act
            subject.putAtRelative(mockBody, relativePosition);

            // Assert
            verify(mockBody).setWorldPosition(expected);
            assertEquals(mockBody, subject.getAtAbsolute(expected[0]));
        }
    }
}