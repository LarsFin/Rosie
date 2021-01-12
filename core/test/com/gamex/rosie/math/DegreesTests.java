package com.gamex.rosie.math;

import com.gamex.rosie.common.WorldConstants._3dDirection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
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

    @Nested
    @DisplayName("From 3d Direction Tests")
    public class From3dDirectionTests {

        @ParameterizedTest
        @ArgumentsSource(DegreesFrom3dDirectionArgumentsProvider.class)
        @DisplayName("Returns correct degrees value from given 3d direction")
        public void returnsDegreesFrom3dDirection(_3dDirection direction, float expectedDegrees) {

            // Act
            float actualDegrees = Degrees.from3dDirection(direction);

            // Assert
            assertEquals(expectedDegrees, actualDegrees);
        }

        @Test
        @DisplayName("Returns -1 when given a 3d direction which cannot be parsed")
        public void returnsFailureWhenInvalidDirection() {

            // Act
            float result = Degrees.from3dDirection(_3dDirection.UP);

            // Assert
            assertEquals(-1f, result);
        }
    }

    private static class DegreesFrom3dDirectionArgumentsProvider implements ArgumentsProvider {

        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {

            return Stream.of(
                    Arguments.of(_3dDirection.NORTH, 0),
                    Arguments.of(_3dDirection.EAST, 90),
                    Arguments.of(_3dDirection.SOUTH, 180),
                    Arguments.of(_3dDirection.WEST, 270)
            );
        }
    }

    @Nested
    @DisplayName("Sum Degrees Tests")
    public class SumDegreesTests {

        @ParameterizedTest
        @ArgumentsSource(SumOfPassedDegreesArgumentsProvider.class)
        @DisplayName("Returns sum of passed degrees values")
        public void sumOfPassedDegrees(float[] degreesValues, float expectedDegreesSum) {

            // Act
            float actualDegreesSum = Degrees.sumDegrees(degreesValues);

            // Assert
            assertEquals(expectedDegreesSum, actualDegreesSum);
        }
    }

    private static class SumOfPassedDegreesArgumentsProvider implements ArgumentsProvider {

        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {

            float[] da1 = { 10f, 20f, 30f };
            float[] da2 = { 180f, 270f, 45f };
            float[] da3 = { 20f, -40f, 8f };

            return Stream.of(
                    Arguments.of(da1, 60f),
                    Arguments.of(da2, 135f),
                    Arguments.of(da3, 348f)
            );
        }
    }
}
