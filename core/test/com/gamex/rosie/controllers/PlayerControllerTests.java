package com.gamex.rosie.controllers;

import com.badlogic.gdx.math.Vector3;
import com.gamex.rosie.common.WorldConstants._3dDirection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerControllerTests {

    private PlayerController subject;

    @BeforeEach
    public void beforeEach() {

        subject = new PlayerController();
    }

    @Nested
    @DisplayName("Get Movement Tests")
    public class GetMovementTests {

        @ParameterizedTest
        @ArgumentsSource(ReturnVectorMovementArgumentsProvider.class)
        @DisplayName("Return correct vector movement from input feed and camera orientation")
        public void returnVectorMovement(_3dDirection inputDirection,
                                         _3dDirection cameraOrientation,
                                         Vector3 expectedMovement) {

            // Act
            Vector3 actualMovement = subject.getMovement(inputDirection, cameraOrientation);

            // Assert
            assertEquals(expectedMovement, actualMovement);
        }
    }

    private static class ReturnVectorMovementArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {

            return Stream.of(
                    Arguments.of(_3dDirection.NONE, _3dDirection.NORTH, Vector3.Zero),
                    Arguments.of(_3dDirection.NONE, _3dDirection.EAST, Vector3.Zero),
                    Arguments.of(_3dDirection.NONE, _3dDirection.SOUTH, Vector3.Zero),
                    Arguments.of(_3dDirection.NONE, _3dDirection.WEST, Vector3.Zero),

                    Arguments.of(_3dDirection.NORTH, _3dDirection.NORTH, Vector3.Y),
                    Arguments.of(_3dDirection.EAST, _3dDirection.NORTH, Vector3.X),
                    Arguments.of(_3dDirection.SOUTH, _3dDirection.NORTH, new Vector3(0f, -1f, 0f)),
                    Arguments.of(_3dDirection.WEST, _3dDirection.NORTH, new Vector3(-1f, 0f, 0f)),

                    Arguments.of(_3dDirection.NORTH, _3dDirection.EAST, Vector3.X),
                    Arguments.of(_3dDirection.EAST, _3dDirection.EAST, new Vector3(0f, -1f, 0f)),
                    Arguments.of(_3dDirection.SOUTH, _3dDirection.EAST, new Vector3(-1f, 0f, 0f)),
                    Arguments.of(_3dDirection.WEST, _3dDirection.EAST, Vector3.Y),

                    Arguments.of(_3dDirection.NORTH, _3dDirection.SOUTH, new Vector3(0f, -1f, 0f)),
                    Arguments.of(_3dDirection.EAST, _3dDirection.SOUTH, new Vector3(-1f, 0f, 0f)),
                    Arguments.of(_3dDirection.SOUTH, _3dDirection.SOUTH, Vector3.Y),
                    Arguments.of(_3dDirection.WEST, _3dDirection.SOUTH, Vector3.X),

                    Arguments.of(_3dDirection.NORTH, _3dDirection.WEST, new Vector3(-1f, 0f, 0f)),
                    Arguments.of(_3dDirection.EAST, _3dDirection.WEST, Vector3.Y),
                    Arguments.of(_3dDirection.SOUTH, _3dDirection.WEST, Vector3.X),
                    Arguments.of(_3dDirection.WEST, _3dDirection.WEST, new Vector3(0f, -1f, 0f))
            );
        }
    }
}
