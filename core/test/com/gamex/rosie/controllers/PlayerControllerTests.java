package com.gamex.rosie.controllers;

import com.badlogic.gdx.math.Vector3;
import com.gamex.rosie.common.Factories.Factory2;
import com.gamex.rosie.common.IWorldBody;
import com.gamex.rosie.common.Transformation;
import com.gamex.rosie.common.WorldConstants._3dDirection;
import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerControllerTests {

    private PlayerController subject;

    private IWorldBody mockBody;
    private TransformationFactory mockTransformationFactory;

    @BeforeEach
    public void beforeEach() {

        mockBody = mock(IWorldBody.class);
        mockTransformationFactory = mock(TransformationFactory.class);

        subject = new PlayerController(mockBody, mockTransformationFactory);
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

    @Nested
    @DisplayName("Get Transformation Tests")
    public class GetTransformationTests {

        @Test
        @DisplayName("Return Transformation with player controlled world body and displacement vector")
        public void returnTransformation() {

            // Arrange
            Vector3 displacement = Vector3.Y;
            Transformation expectedTransformation = mock(Transformation.class);

            when(mockTransformationFactory.build(mockBody, displacement)).thenReturn(expectedTransformation);

            // Act
            Transformation actualTransformation = subject.getTransformation(displacement);

            // Assert
            assertEquals(expectedTransformation, actualTransformation);
        }
    }

    @Nested
    @DisplayName("Resolve Movement Direction Tests")
    public class ResolveMovementDirectionTests {

        @ParameterizedTest
        @ArgumentsSource(ReturnResolvedDirectionArgumentsProvider.class)
        @DisplayName("Return correct direction from passed input values")
        public void returnResolvedDirection(int horizontalInputFeed,
                                            int verticalInputFeed,
                                            _3dDirection expectedDirection) {

            // Act
            _3dDirection actualDirection = subject.resolveMovementDirection(horizontalInputFeed, verticalInputFeed);

            // Assert
            assertEquals(expectedDirection, actualDirection);
        }
    }

    private static class ReturnResolvedDirectionArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {

            return Stream.of(
                    Arguments.of(0, 0, _3dDirection.NONE),
                    Arguments.of(1, 0, _3dDirection.EAST),
                    Arguments.of(0, 1, _3dDirection.NORTH),
                    Arguments.of(-1, 0, _3dDirection.WEST),
                    Arguments.of(0, -1, _3dDirection.SOUTH),

                    Arguments.of(1, 1, _3dDirection.NORTH),
                    Arguments.of(-1, 1, _3dDirection.NORTH),
                    Arguments.of(1, -1, _3dDirection.EAST),
                    Arguments.of(-1, -1, _3dDirection.SOUTH)
            );
        }
    }

    private interface TransformationFactory extends Factory2<Transformation, IWorldBody, Vector3> {

        Transformation build(IWorldBody worldBody, Vector3 displacement);
    }
}
