package com.gamex.rosie.math;

import com.gamex.rosie.common.WorldConstants._3dDirection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class DegreesTests {

    @Nested
    @DisplayName("As 3d Direction Tests")
    public class As3dDirectionTests {

        @ParameterizedTest
        @ArgumentsSource(Correct3dDirectionArgumentsProvider.class)
        @DisplayName("Returns correct three dimensional direction from degrees as float")
        public void correct3dDirectionFromDegrees(float degrees, _3dDirection expectedDirection) {

            // Act
            _3dDirection actualDirection = Degrees.as3dDirection(degrees);

            // Assert
            assertEquals(expectedDirection, actualDirection);
        }
    }

    private static class Correct3dDirectionArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {

            return Stream.of(
                    Arguments.of(0f, _3dDirection.NORTH),
                    Arguments.of(90f, _3dDirection.EAST),
                    Arguments.of(180f, _3dDirection.SOUTH),
                    Arguments.of(270f, _3dDirection.WEST),

                    Arguments.of(45f, _3dDirection.EAST),
                    Arguments.of(135f, _3dDirection.SOUTH),
                    Arguments.of(225f, _3dDirection.WEST),
                    Arguments.of(315f, _3dDirection.NORTH),

                    Arguments.of(360f, _3dDirection.NORTH),
                    Arguments.of(810f, _3dDirection.EAST),
                    Arguments.of(495f, _3dDirection.SOUTH),
                    Arguments.of(585f, _3dDirection.WEST),

                    Arguments.of(-45f, _3dDirection.NORTH),
                    Arguments.of(-270f, _3dDirection.EAST),
                    Arguments.of(-225f, _3dDirection.SOUTH),
                    Arguments.of(-50f, _3dDirection.WEST)
            );
        }
    }
}
