package com.gamex.rosie.controllers;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static com.gamex.rosie.common.WorldConstants._3dDirection;
import static com.gamex.rosie.common.WorldConstants.RotationalDirection;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CameraControllerTests {

    private CameraController subject;

    @Nested
    @DisplayName("Constructor Tests")
    public class ConstructorTests {

        @Test
        @DisplayName("Set default orientation to NORTH")
        public void defaultOrientationOfNorth() {

            // Act
            subject = new CameraController();

            // Assert
            assertEquals(_3dDirection.NORTH, subject.getOrientation());
        }

        @Test
        @DisplayName("Set custom orientation with parameter")
        public void setCustomOrientation() {

            // Arrange
            _3dDirection customOrientation = _3dDirection.EAST;

            // Act
            subject = new CameraController(customOrientation);

            // Assert
            assertEquals(customOrientation, subject.getOrientation());
        }
    }

    @Nested
    @DisplayName("Turn Tests")
    public class TurnTests {

        @ParameterizedTest
        @ArgumentsSource(TurnTestArgumentsProvider.class)
        @DisplayName("Turn orientation of camera correctly")
        public void turnOrientationCorrectly(_3dDirection initialOrientation,
                                             RotationalDirection rotationalDirection,
                                             _3dDirection expectedOrientation) {

            // Arrange
            subject = new CameraController(initialOrientation);

            // Act
            subject.turn(rotationalDirection);

            // Assert
            assertEquals(expectedOrientation, subject.getOrientation());
        }
    }

    private static class TurnTestArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {

            return Stream.of(
                    Arguments.of(_3dDirection.NORTH, RotationalDirection.CLOCKWISE, _3dDirection.EAST),
                    Arguments.of(_3dDirection.EAST, RotationalDirection.CLOCKWISE, _3dDirection.SOUTH),
                    Arguments.of(_3dDirection.SOUTH, RotationalDirection.CLOCKWISE, _3dDirection.WEST),
                    Arguments.of(_3dDirection.WEST, RotationalDirection.CLOCKWISE, _3dDirection.NORTH),

                    Arguments.of(_3dDirection.NORTH, RotationalDirection.ANTI_CLOCKWISE, _3dDirection.WEST),
                    Arguments.of(_3dDirection.WEST, RotationalDirection.ANTI_CLOCKWISE, _3dDirection.SOUTH),
                    Arguments.of(_3dDirection.SOUTH, RotationalDirection.ANTI_CLOCKWISE, _3dDirection.EAST),
                    Arguments.of(_3dDirection.EAST, RotationalDirection.ANTI_CLOCKWISE, _3dDirection.NORTH)
            );
        }
    }
}
