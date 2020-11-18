package com.gamex.rosie.math;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VectorsTests {

    @Nested
    @DisplayName("Tests for checking the addition of various vectors")
    public class AddTests {

        @Test
        @DisplayName("Can add a two dimensional vector to a three dimensional vector")
        public void addVector2dToVector3d() {

            // Arrange
            Vector3 v1 = new Vector3(1, 5, -2);
            Vector2 v2 = new Vector2(8, 8);
            Vector3 expected = new Vector3(9, 13, -2);

            // Act
            Vector3 result = Vectors.add(v1, v2);

            // Assert
            assertEquals(expected, result);
        }

        @Test
        @DisplayName("Can add three dimensional vectors to form three dimensional vector result")
        public void add2Vector3d() {

            // Arrange
            Vector3 v1 = new Vector3(3, 3, 5);
            Vector3 v2 = new Vector3(5, 0, 1);
            Vector3 expected = new Vector3(8, 3, 6);

            // Act
            Vector3 result = Vectors.add(v1, v2);

            // Assert
            assertEquals(expected, result);
        }
    }
}
