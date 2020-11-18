package com.gamex.rosie.logging;

public final class Messages {

    private Messages() {}

    public static class Map {

        public static class Error {

            public static String InvalidAbsolutePositionSet(int currentPosLength, int setPosLength) {

                return "Invalid Position Set. Unequal number of positions between current and set.\n" +
                        "Current Positions Count: " +
                        currentPosLength +
                        "\nSet Positions Count: " +
                        setPosLength;
            }
        }
    }
}
