package com.gamex.rosie.controllers;

import com.badlogic.gdx.Input;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InputControllerTests {

    private InputController subject;

    private Input mockInput;

    @BeforeEach
    public void beforeEach() {

        mockInput = mock(Input.class);

        subject = new InputController(mockInput);
    }

    @Nested
    @DisplayName("Get Horizontal Tests")
    public class GetHorizontalTests {

        @Test
        @DisplayName("No Hardware Input results in Neutral Horizontal")
        public void neutralValueFromNone() {

            // Arrange
            when(mockInput.isKeyPressed(Input.Keys.LEFT)).thenReturn(false);
            when(mockInput.isKeyPressed(Input.Keys.RIGHT)).thenReturn(false);

            // Act
            int result = subject.getHorizontal();

            // Assert
            assertEquals(0, result);
        }

        @Test
        @DisplayName("Left Hardware Input results in Negative Horizontal")
        public void negativeValueFromLeft() {

            // Arrange
            when(mockInput.isKeyPressed(Input.Keys.LEFT)).thenReturn(true);
            when(mockInput.isKeyPressed(Input.Keys.RIGHT)).thenReturn(false);

            // Act
            int result = subject.getHorizontal();

            // Assert
            assertEquals(-1, result);
        }

        @Test
        @DisplayName("Right Hardware Input results in Positive Horizontal")
        public void positiveValueFromRight() {

            // Arrange
            when(mockInput.isKeyPressed(Input.Keys.LEFT)).thenReturn(false);
            when(mockInput.isKeyPressed(Input.Keys.RIGHT)).thenReturn(true);

            // Act
            int result = subject.getHorizontal();

            // Assert
            assertEquals(1, result);
        }

        @Test
        @DisplayName("Both Hardware Input results in Neutral Horizontal")
        public void neutralValueFromBoth() {

            // Arrange
            when(mockInput.isKeyPressed(Input.Keys.LEFT)).thenReturn(true);
            when(mockInput.isKeyPressed(Input.Keys.RIGHT)).thenReturn(true);

            // Act
            int result = subject.getHorizontal();

            // Assert
            assertEquals(0, result);
        }
    }

    @Nested
    @DisplayName("Get Vertical Tests")
    public class GetVerticalTests {

        @Test
        @DisplayName("No Hardware Input results in Neutral Vertical")
        public void neutralValueFromNone() {

            // Arrange
            when(mockInput.isKeyPressed(Input.Keys.DOWN)).thenReturn(false);
            when(mockInput.isKeyPressed(Input.Keys.UP)).thenReturn(false);

            // Act
            int result = subject.getVertical();

            // Assert
            assertEquals(0, result);
        }

        @Test
        @DisplayName("Down Hardware Input results in Negative Vertical")
        public void negativeValueFromLeft() {

            // Arrange
            when(mockInput.isKeyPressed(Input.Keys.DOWN)).thenReturn(true);
            when(mockInput.isKeyPressed(Input.Keys.UP)).thenReturn(false);

            // Act
            int result = subject.getVertical();

            // Assert
            assertEquals(-1, result);
        }

        @Test
        @DisplayName("Up Hardware Input results in Positive Vertical")
        public void positiveValueFromRight() {

            // Arrange
            when(mockInput.isKeyPressed(Input.Keys.DOWN)).thenReturn(false);
            when(mockInput.isKeyPressed(Input.Keys.UP)).thenReturn(true);

            // Act
            int result = subject.getVertical();

            // Assert
            assertEquals(1, result);
        }

        @Test
        @DisplayName("Both Hardware Input results in Neutral Vertical")
        public void neutralValueFromBoth() {

            // Arrange
            when(mockInput.isKeyPressed(Input.Keys.DOWN)).thenReturn(true);
            when(mockInput.isKeyPressed(Input.Keys.UP)).thenReturn(true);

            // Act
            int result = subject.getVertical();

            // Assert
            assertEquals(0, result);
        }
    }
}
