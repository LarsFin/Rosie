package com.gamex.rosie.map;

import com.badlogic.gdx.math.Vector3;
import com.gamex.rosie.common.IWorldBody;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

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
            subject.putAtAbsolute(instantPos, mockBody);

            // Act
            CheckResult result = subject.checkEmptyRelative(pos, relOffset);

            // Assert
            assertEquals(CheckResult.OCCUPIED, result);
        }
    }
}